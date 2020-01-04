package com.example.pebblewear

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.support.wearable.input.RotaryEncoder
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.wear.widget.WearableRecyclerView
import kotlin.math.roundToInt
import kotlin.random.Random

class Overview : WearableActivity(), OverviewAdapter.OnGradientListener  {

    lateinit var all: ArrayList<HashMap<String, String>>

    lateinit var gradientList: WearableRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_layout)

        val snapHelper = LinearSnapHelper()
        gradientList = this.findViewById(R.id.gradientList)
        gradientList.setOnGenericMotionListener(View.OnGenericMotionListener { v, ev ->
            if (ev.action == MotionEvent.ACTION_SCROLL && RotaryEncoder.isFromRotaryEncoder(ev)) {
                val delta = -RotaryEncoder.getRotaryAxisValue(ev) *
                        RotaryEncoder.getScaledScrollFactor(applicationContext) * Values.crownScrollSpeed

                v.scrollBy(0, delta.roundToInt())

                return@OnGenericMotionListener true
            }

            false
        })

        gradientList.requestFocus()
        snapHelper.attachToRecyclerView(gradientList)

        // Enables Always-on
        setAmbientEnabled()
        get()

    }

    fun get() {
        all = intent.getSerializableExtra("list") as ArrayList<HashMap<String, String>>
        val displayAdapter = OverviewAdapter(applicationContext, all, this)
        val layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        gradientList.layoutManager = layoutManager
        gradientList.adapter = displayAdapter
        //Log.e("Info", all.toString())
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (event.repeatCount == 0) {
            when (keyCode) {
                KeyEvent.KEYCODE_STEM_1 -> {
                    hardwareButton(0)
                    true
                }
                KeyEvent.KEYCODE_STEM_2 -> {
                    hardwareButton((0 until all.size).random())
                    true
                }
                else -> {
                    super.onKeyDown(keyCode, event)
                }
            }
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onGradientClick(position: Int) {
        Log.d("DEBUG", "clicked: $position")
        /*var details = Intent(this, Details::class.java)
        var gradientName = */
    }

    private fun hardwareButton(position: Int) {
        if (Values.instantScroll) {
            gradientList.scrollToPosition(position)
        } else {
            gradientList.smoothScrollToPosition(position)
        }
    }
}

