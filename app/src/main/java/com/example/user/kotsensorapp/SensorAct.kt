package com.example.user.kotsensorapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sensor.*

class SensorAct : AppCompatActivity(), SensorEventListener {

    var manager: SensorManager? = null
    var s: Sensor? = null

    var xold = 0.0
    var yold = 0.0
    var zold = 0.0
    var ts = 3000.0
    var oldTime: Long = 0

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent) {
//        textView.text = "X:" + p0.values[0]
//        textView2.text = "Y:" + p0.values[1]
//        textView3.text = "Z:" + p0.values[2]

        var x = p0.values[0]
        var y = p0.values[1]
        var z = p0.values[2]

        var ct = System.currentTimeMillis()
        if ((ct - oldTime) > 100) {

            var timeDiff = ct - oldTime
            oldTime = ct
            var speed = Math.abs(x + y + z - xold - yold - zold) / timeDiff * 10000
            if (speed > ts) {
                var v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                v.vibrate(500)
            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        s = manager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    override fun onResume() {
        super.onResume()
        manager!!.registerListener(this, s,
                SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        manager!!.unregisterListener(this)
    }
}
