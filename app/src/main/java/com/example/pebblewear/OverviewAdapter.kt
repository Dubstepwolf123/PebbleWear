package com.example.pebblewear

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class OverviewAdapter(context: Context, map: ArrayList<HashMap<String, String>>) :
    RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {

    private var context: Context = context
    private var map: ArrayList<HashMap<String, String>> = map
    private var layoutInflater: LayoutInflater

    override fun getItemCount(): Int {
        try {
            return map.size
        } catch (e: Exception) {
            Log.e("ERR", "pebble_wear.overview_adapter.get_item_count: " + e.localizedMessage)
        }
        return 0
    }

    init {
        this.layoutInflater =
            this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = this.layoutInflater.inflate(R.layout.gradient_display, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val details: HashMap<String, String> = map[position]
            val startColour = Color.parseColor(details["startColour"])
            val endColour = Color.parseColor(details["endColour"])

            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(startColour, endColour)
            )
            gradientDrawable.shape = GradientDrawable.OVAL
            holder.gradient.background = gradientDrawable
            holder.gradient.transitionName = details["backgroundName"]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                holder.gradient.outlineSpotShadowColor = endColour
                holder.gradient.outlineAmbientShadowColor = endColour
            }
        } catch (e: Exception) {
            Log.e("ERR", "pebble_wear.overview_adapter.on_bind_view_holder: " + e.localizedMessage)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var gradient: ImageView = itemView.findViewById(R.id.gradientDisplay)
    }


}