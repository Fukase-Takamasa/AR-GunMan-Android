package com.takamasafukase.ar_gunman_android.viewModel

import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takamasafukase.ar_gunman_android.model.AndroidToUnityMessage
import com.takamasafukase.ar_gunman_android.model.AndroidToUnityMessageEventType
import com.takamasafukase.ar_gunman_android.model.UnityToAndroidMessage
import com.takamasafukase.ar_gunman_android.model.UnityToAndroidMessageEventType
import com.takamasafukase.ar_gunman_android.model.WeaponType
import com.takamasafukase.ar_gunman_android.manager.AudioManager
import com.takamasafukase.ar_gunman_android.manager.MotionDetector
import com.takamasafukase.ar_gunman_android.R
import com.takamasafukase.ar_gunman_android.UnityToAndroidMessenger
import com.takamasafukase.ar_gunman_android.manager.CurrentWeapon
import com.takamasafukase.ar_gunman_android.manager.ScoreCounter
import com.takamasafukase.ar_gunman_android.manager.TimeCounter
import com.takamasafukase.ar_gunman_android.utility.TimeCountUtil
import com.unity3d.player.UnityPlayer
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.ref.WeakReference

data class GameViewState(
    val isLoading: Boolean,
    val isShowTutorialDialog: Boolean,
    val isShowWeaponChangeDialog: Boolean,
    val timeCountText: String,
    val bulletsCountImageResourceId: Int,
)

class GameViewModel(
    sensorManager: SensorManager,
    private val audioManager: AudioManager,
    private val timeCounter: TimeCounter,
    private val timeCountUtil: TimeCountUtil,
    private val currentWeapon: CurrentWeapon,
    private val scoreCounter: ScoreCounter,
) : ViewModel(), UnityToAndroidMessenger.MessageReceiverFromUnity {

    private lateinit var motionDetector: MotionDetector
    private val _state = MutableStateFlow(
        GameViewState(
            isLoading = true,
            isShowTutorialDialog = false,
            isShowWeaponChangeDialog = false,
            timeCountText = "",
            bulletsCountImageResourceId = 0,
        )
    )
    val state = _state.asStateFlow()
    private val _showResultScreen = MutableSharedFlow<Unit>()
    val showResultScreen = _showResultScreen.asSharedFlow()
    private val onReceivedTargetHitEvent = MutableSharedFlow<Unit>()
    private var currentWeaponType = WeaponType.PISTOL

    init {
        showLoadingToHideUnityLogoSplash()
        handleMotionDetector(sensorManager = sensorManager)
        // Unityからのメッセージの受け口になるオブジェクトの受け手として自身を弱参照で登録
        UnityToAndroidMessenger.receiver = WeakReference(this)

        viewModelScope.launch {
            _state
                // isLoadingの値だけのflowに変換
                .map { MutableStateFlow(it.isLoading) }
                // falseの場合のみ通す
                .filter { isLoading -> !isLoading.value}
                // 最初の一回だけを通す
                .first()
                .collect {
                    // 初回のロードが終わった最初の1回だけを検知し、チュートリアル状態のチェックをする
                    checkTutorialSeenStatus()
                }
        }

        viewModelScope.launch {
            onReceivedTargetHitEvent
                .debounce(50)
                .collect {
                    handleTargetHit()
                }
        }

        viewModelScope.launch {
            timeCounter.countChanged
                .collect {
                    _state.value = _state.value.copy(
                        timeCountText = timeCountUtil.getTwoDigitTimeCountText(it)
                    )
                }
        }

        viewModelScope.launch {
            timeCounter.countEnded
                .collect {
                    // 終了音声を再生
                    audioManager.playSound(R.raw.end_whistle)

                    // タイマーを破棄
                    timeCounter.disposeTimer()

                    // モーション検知を終了
                    motionDetector.stopUpdate()

                    // 1.5秒後に結果画面に遷移指示を流す
                    Handler(Looper.getMainLooper()).postDelayed({
                        // 結果画面と名前登録ダイアログの出現音声を再生
                        audioManager.playSound(R.raw.ranking_appear)

                        viewModelScope.launch {
                            // 遷移指示を流す
                            _showResultScreen.emit(Unit)
                        }
                    }, 1500)
                }
        }

        viewModelScope.launch {
            currentWeapon.weaponTypeChanged
                .collect {
                    _state.value = _state.value.copy(
                        bulletsCountImageResourceId = it.getBulletsCountImageResourceId(
                            // 現在の残弾数に応じた画像を設定
                            count = currentWeapon.bulletsCountChanged.value
                        )
                    )

                    // TODO: UnityにshowWeaponの通知を送る（これは武器が2つ以上に増えた時に実装する）
                }
        }

        viewModelScope.launch {
            currentWeapon.fired
                .collect {
                    // 現在の武器の射撃命令のメッセージを作成
                    val toUnityMessage = AndroidToUnityMessage(
                        eventType = AndroidToUnityMessageEventType.FIRE_WEAPON,
                        weaponType = currentWeaponType,
                    )
                    // JSON文字列に変換
                    val jsonString = Json.encodeToString(toUnityMessage)
                    // Unityへ通知を送る
                    UnityPlayer.UnitySendMessage("XR Origin", "OnReceiveMessageFromAndroid", jsonString)
                }
        }
    }

    fun onTapWeaponChangeButton() {
        _state.value = _state.value.copy(isShowWeaponChangeDialog = true)
    }

    fun onCloseWeaponChangeDialog() {
        _state.value = _state.value.copy(isShowWeaponChangeDialog = false)

        // 現在の武器の表示音声を鳴らす（武器が変更されずにただcloseやエッジスワイプで閉じられた時も含めるためここで呼び出している）
        audioManager.playSound(currentWeaponType.setSoundResourceId)
    }

    fun onSelectWeapon(selectedWeapon: WeaponType) {
        // 今は一旦ピストル以外は弾く
        if (selectedWeapon != WeaponType.PISTOL) {
            return
        }

        // currentWeaponTypeを更新する
        currentWeaponType = selectedWeapon

        // Unityへ武器表示の通知を送る
        // TODO: ここは武器が2つ以上に増えた時に実装する。今は武器の切り替えが無いので実装不要。

        // ダイアログを閉じる
        onCloseWeaponChangeDialog()
    }

    override fun onMessageReceivedFromUnity(message: String) {
        Log.d("Android", "ログAndroid: GameVM onMessageReceivedFromUnity message: $message")

        val fromUnityMessage = Json.decodeFromString<UnityToAndroidMessage>(message)
        Log.d("Android", "ログAndroid: GameVM fromUnityMessage: $fromUnityMessage, eventType: ${fromUnityMessage.eventType}")

        when (fromUnityMessage.eventType) {
            UnityToAndroidMessageEventType.TARGET_HIT -> {
                viewModelScope.launch {
                    onReceivedTargetHitEvent.emit(Unit)
                }
            }
        }
    }

    // Unityビューを起動後にUnityロゴのスプラッシュが出るので、その間は黒背景とインジケータで隠す
    private fun showLoadingToHideUnityLogoSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            _state.value = _state.value.copy(isLoading = false)
        }, 3000)
    }

    private fun checkTutorialSeenStatus() {
        // TODO: Tutorialを見たかどうかをSharedPrefに保存＆確認して処理の分岐

        // TODO: まだチュートリアルを見ていない時の処理　チュートリアルダイアログの表示

        // すでにチュートリアルを見終わっている時の処理
        // デフォルトの武器を選択
        onSelectWeapon(selectedWeapon = WeaponType.PISTOL)

        // 1.5秒後にタイマーを開始
        Handler(Looper.getMainLooper()).postDelayed({
            // スタート音声を再生
            audioManager.playSound(R.raw.start_whistle)

            // タイマーを開始
            timeCounter.startTimer()
        }, 1500)
    }

    private fun handleMotionDetector(sensorManager: SensorManager) {
        motionDetector = MotionDetector(
            sensorManager = sensorManager,
            onDetectWeaponFiringMotion = {
                // 現在の武器に発射処理を行わせる
                currentWeapon.fire()
            },
            onDetectWeaponReloadingMotion = {
                // 現在の武器にリロード処理を行わせる
                currentWeapon.reload()
            }
        )
    }

    private fun handleTargetHit() {
        // ターゲットヒット時の音声を再生
        audioManager.playSound(R.raw.head_shot)
        // 現在の武器に応じた得点の加算処理を行わせる
        scoreCounter.addScore(weaponType = currentWeaponType)
    }
}

