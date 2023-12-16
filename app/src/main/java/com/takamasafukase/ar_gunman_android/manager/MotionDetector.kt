package com.takamasafukase.ar_gunman_android.manager

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class MotionDetector(
    private val sensorManager: SensorManager,
    val onDetectWeaponFiringMotion: () -> Unit,
    val onDetectWeaponReloadingMotion: () -> Unit,
    ) : SensorEventListener {
    // 発射動作の判定では加速度＋ジャイロも使うので、最新の値としてここに格納して使う
    private var gyroCompositeValue = 0f
    // センサーがアップデートされた回数を記録（加速度とジャイロで共通）
    private var sensorUpdatedCount = 0
    // 前回発射動作を検知して時点でのアップデートカウントがいくつだったかを保持しておく
    private var previousDetectFiringMotionCount = 0
    // 前回リロード動作を検知して時点でのアップデートカウントがいくつだったかを保持しておく
    private var previousDetectReloadingMotionCount = 0

    init {
        registerListeners()
    }

    override fun onSensorChanged(event: SensorEvent) {
        // センサーのアップデート回数をインクリメント
        sensorUpdatedCount++
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            handleUpdatedAccelerationData(
                compositeValue = getCompositeValue(
                    x = 0f,
                    y = event.values[1],
                    z = event.values[2],
                ),
                gyroZSquaredValue = gyroCompositeValue,
            )
        } else if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            // 加速度の方の判定でジャイロも使うので格納する
            gyroCompositeValue = getCompositeValue(
                x = 0f,
                y = 0f,
                z = event.values[2]
            )
            handleUpdatedGyroData(compositeValue = gyroCompositeValue)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    fun stopUpdate() {
        // Listenerを解除
        sensorManager.unregisterListener(this)
    }

    private fun registerListeners() {
        // 加速度Listenerの登録
        val acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (acceleration != null) {
            sensorManager.registerListener(this, acceleration, SensorManager.SENSOR_DELAY_UI)
        }else {
            Log.d("debug", "TYPE_ACCELEROMETER not supported")
        }
        // ジャイロListenerの登録
        val gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (gyro != null) {
            sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_UI)
        } else {
            Log.d("debug", "TYPE_GYROSCOPE not supported")
        }
    }

    private fun handleUpdatedAccelerationData(
        compositeValue: Float,
        gyroZSquaredValue: Float,
    ) {
        if ((compositeValue >= 300 && gyroZSquaredValue < 10) &&
            // 前回の発射動作検知から50回のアップデートが経過しているかチェック
            (sensorUpdatedCount - previousDetectFiringMotionCount >= 50)) {
            previousDetectFiringMotionCount = sensorUpdatedCount
            onDetectWeaponFiringMotion()
        }
    }

    private fun handleUpdatedGyroData(
        compositeValue: Float,
    ) {
        if ((compositeValue >= 30) &&
            // 前回のリロード動作検知から50回のアップデートが経過しているかチェック
            (sensorUpdatedCount - previousDetectReloadingMotionCount >= 50)) {
            previousDetectReloadingMotionCount = sensorUpdatedCount
            onDetectWeaponReloadingMotion()
        }
    }

    private fun getCompositeValue(x: Float, y: Float, z: Float): Float {
        return (x * x) + (y * y) + (z * z)
    }
}

