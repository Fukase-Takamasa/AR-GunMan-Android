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
    private var sensorUpdatedCount = 0
    private var previousDetectFiringMotionCount = 0
    private var previousDetectReloadingMotionCount = 0

    init {
        registerListeners()
    }

    override fun onSensorChanged(event: SensorEvent) {
        Log.d("debug", "onSensorChanged")
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
            (sensorUpdatedCount - previousDetectFiringMotionCount >= 50)) {
            previousDetectFiringMotionCount = sensorUpdatedCount
            onDetectPistolFiringMotion()
        }
    }

    private fun handleUpdatedGyroData(
        compositeValue: Float,
    ) {
        if ((compositeValue >= 30) &&
            (sensorUpdatedCount - previousDetectReloadingMotionCount >= 50)) {
            previousDetectReloadingMotionCount = sensorUpdatedCount
            onDetectPistolReloadingMotion()
        }
    }

    private fun getCompositeValue(x: Float, y: Float, z: Float): Float {
        return (x * x) + (y * y) + (z * z)
    }
}

