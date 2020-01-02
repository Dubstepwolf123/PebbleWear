package com.example.pebblewear

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.support.wearable.input.RotaryEncoder
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class Overview : WearableActivity() {

    lateinit var items: Bundle
    lateinit var all: ArrayList<HashMap<String, String>>
    
    lateinit var gradientList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        val snapHelper = LinearSnapHelper()
        gradientList = this.findViewById(R.id.gradientList)
        gradientList.setOnGenericMotionListener(View.OnGenericMotionListener { v, ev ->
            if (ev.action == MotionEvent.ACTION_SCROLL && RotaryEncoder.isFromRotaryEncoder(ev)) {
                val delta = -RotaryEncoder.getRotaryAxisValue(ev) *
                        RotaryEncoder.getScaledScrollFactor(applicationContext) * 2

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

    fun get(){
        all = intent.getSerializableExtra("list") as ArrayList<HashMap<String, String>>
        val displayAdapter = OverviewAdapter(applicationContext, all)
        val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        gradientList.layoutManager = layoutManager
        gradientList.adapter = displayAdapter
        //Log.e("Info", all.toString())
    }
}

