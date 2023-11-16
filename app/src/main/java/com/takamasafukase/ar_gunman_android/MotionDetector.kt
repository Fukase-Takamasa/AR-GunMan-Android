package com.takamasafukase.ar_gunman_android

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class MotionDetector(
    private val sensorManager: SensorManager,
    val onDetectPistolFiringMotion: () -> Unit,
    val onDetectPistolReloadingMotion: () -> Unit,
    ) : SensorEventListener {
    private var gyroCompositeValue = 0f

    init {
        registerListeners()
    }

    override fun onSensorChanged(event: SensorEvent) {
        Log.d("debug", "onSensorChanged")
        if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
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
        Log.d("Android", "ログAndroid: handleUpdatedAccelerationData: compositeValue: $compositeValue, gyroZSquaredValue: $gyroZSquaredValue")
        if (compositeValue >= 1.5 && gyroZSquaredValue < 10) {
            print("加速度　ピストル発射")
            onDetectPistolFiringMotion()
        }else {
            print("加速度　スルー")
        }
    }

    private fun handleUpdatedGyroData(
        compositeValue: Float,
    ) {
        Log.d("Android", "ログAndroid: handleUpdatedGyroData: compositeValue: $compositeValue")
        if (compositeValue >= 10) {
            print("ジャイロ　ピストルリロード")
            onDetectPistolReloadingMotion()
        }else {
            print("ジャイロ　スルー")
        }
    }

    private fun getCompositeValue(x: Float, y: Float, z: Float): Float {
        return (x * x) + (y * y) + (z * z)
    }
}

