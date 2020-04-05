package com.jjj.stopwatch_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var index :Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_start.setOnClickListener {
            isRunning = !isRunning
            if (isRunning) start() else pause()
        }

        fab_reset.setOnClickListener {
            reset()
        }

        btn_lab.setOnClickListener {
            if(time!=0) lapTime()
        }
    }

    private fun start() {
        fab_start.setImageResource(R.drawable.ic_pause)

        timerTask = kotlin.concurrent.timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100

            runOnUiThread {
                secText.text = "$sec"
                milliText.text = "$milli"
            }
        }
    }

    private fun pause() {
        fab_start.setImageResource(R.drawable.ic_play)
        timerTask?.cancel();
    }

    private fun reset() {
        timerTask?.cancel()

        time = 0
        isRunning = false
        fab_start.setImageResource(R.drawable.ic_play)
        secText.text = "0"
        milliText.text = "00"

        lap_Layout.removeAllViews()
        index = 1
    }

    private fun lapTime() {
        val lapTime = time
        val textView = TextView(this).apply {
            setTextSize(20f)
            text = "${lapTime / 100}.${lapTime % 100}"
        }
        lap_Layout.addView(textView,0)
        index++
    }
}
