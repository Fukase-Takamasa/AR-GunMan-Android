package com.takamasafukase.ar_gunman_android

//import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import java.util.Locale

class MotionDetector(private val sensorManager: SensorManager) : SensorEventListener {
    init {
        // Listenerの登録
        val gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (gyro != null) {
            sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_UI)
        } else {
            Log.d("debug", "TYPE_GYROSCOPE not supported")
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        Log.d("debug", "onSensorChanged")
        if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            val sensorX = event.values[0]
            val sensorY = event.values[1]
            val sensorZ = event.values[2]
            val strTmp = String.format(
                Locale.US, """Gyroscope
  X: %f
 Y: %f
 Z: %f""", sensorX, sensorY, sensorZ
            )
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    fun stopUpdate() {
        // Listenerを解除
        sensorManager.unregisterListener(this)
    }
}

