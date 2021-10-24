package com.example.proximitysensorlab2

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.icu.number.Scale
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {
    lateinit var sensorData: TextView
    lateinit var sensorRange: TextView
    lateinit var imageView: ImageView
    lateinit var sm: SensorManager
    var sensor : Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorData = findViewById(R.id.tvSensorData)
        sensorRange = findViewById(R.id.tvSensorRange)
        imageView = findViewById(R.id.imageView)
        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        sensor?.let {
            sm.registerListener(this,it,SensorManager.SENSOR_DELAY_UI)
        }


    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_PROXIMITY){
            val distance = event.values.first()
            val max = event.sensor.maximumRange
            sensorData.text = distance.toString()
            sensorRange.text = max.toString()
            if (distance > 0){
                ObjectAnimator.ofFloat(imageView,"scaleX",3f).apply {
                    duration = 1000
                    start()
                }
                ObjectAnimator.ofFloat(imageView,"scaleY",3f).apply {
                    duration = 1000
                    start()
                }
            }else{
                ObjectAnimator.ofFloat(imageView,"scaleX",0.5f).apply {
                    duration = 1000
                    start()
                }
                ObjectAnimator.ofFloat(imageView,"scaleY",0.5f).apply {
                    duration = 1000
                    start()
                }

            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

}