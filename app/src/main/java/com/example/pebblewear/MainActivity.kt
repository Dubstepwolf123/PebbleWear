package com.example.pebblewear

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.lang.Exception

class MainActivity : WearableActivity() {

    private lateinit var animationView:ImageView

    private lateinit var display: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()
        setUI()
        getData()
        display = Intent(this, Overview::class.java)
    }

    fun setUI(){
        /*animationView = findViewById(R.id.animationView)
        animationView.setBackgroundResource(R.drawable.loading_animation)
        val animationDrawable = animationView.background as AnimationDrawable
        animationDrawable.start()*/
    }

    fun getData(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://script.google.com/macros/s/AKfycbwFkoSBTbmeB6l9iIiZWGczp9sDEjqX0jiYeglczbLKFAXsmtB1/exec?action=getItems"

        val request = StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
                Toast.makeText(this, "here", Toast.LENGTH_SHORT).show()

            try {
                var strResponse = response.toString()
                val jsonOBJ = JSONObject(strResponse)
                val jsonArray = jsonOBJ.getJSONArray("items")
                val list: ArrayList<HashMap<String, String>> = ArrayList()

                for (i in 0 until jsonArray.length()) {
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
            }catch (e:Exception){
                Log.e("Err", "pebble_wear.main_activity: " + e.localizedMessage)
            }


            },
            Response.ErrorListener {})
        queue.add(request)
    }
}
