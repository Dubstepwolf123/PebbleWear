package com.example.pebblewear

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Connecting : WearableActivity() {

    private lateinit var display: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connecting_layout)
        Values.loadValues(this)

        // Enables Always-on
        setAmbientEnabled()
        getData()
        display = Intent(this, Overview::class.java)
    }

    fun getData() {
        val queue = Volley.newRequestQueue(this)
        val url =
            "https://script.google.com/macros/s/AKfycbwFkoSBTbmeB6l9iIiZWGczp9sDEjqX0jiYeglczbLKFAXsmtB1/exec?action=getItems"

        val request = StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->

            try {
                var strResponse = response.toString()
                val jsonOBJ = JSONObject(strResponse)
                val jsonArray = jsonOBJ.getJSONArray("items")
                val list: ArrayList<HashMap<String, String>> = ArrayList()

                for (i in jsonArray.length() - 1 downTo 0) {
                    var jsonInner: JSONObject = jsonArray.getJSONObject(i)

                    var str_background = jsonInner.getString("backgroundName")
                    var str_startColour = jsonInner.getString("leftColour")
                    var str_endColour = jsonInner.getString("rightColour")
                    var str_description = jsonInner.getString("description")

                    var items: HashMap<String, String> = HashMap()
                    items["backgroundName"] = str_background
                    items["startColour"] = str_startColour
                    items["endColour"] = str_endColour
                    items["description"] = str_description

                    list.add(items)
                    display.putExtra("list", list)
                }
                startActivity(display)
                finish()
            } catch (e: Exception) {
                Log.e("Err", "pebble_wear.main_activity: " + e.localizedMessage)
            }


        },
            Response.ErrorListener {})
        queue.add(request)
    }
}
