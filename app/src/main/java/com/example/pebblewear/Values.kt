package com.example.pebblewear

import android.content.Context

object Values {

    private val SAVE = "SavedValues"

    //If lastVersion = 0 then go to welcome screen
    //If lastVersion < (currentVersion) go to what's new screen
    //If lastVersion = (currentVersion) go to Overview
    var lastVersion:Int = 0
    var crownScrollSpeed:Float = 1.5f
    var instantScroll:Boolean = false

    fun saveValues(context: Context) {
        val sharedPreferences = context.getSharedPreferences(SAVE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("lastVersion", lastVersion)
        editor.putFloat("crownScrollSpeed", crownScrollSpeed)
        editor.putBoolean("instantScroll", instantScroll)
        editor.apply()
    }

    fun loadValues(context: Context) {
        val sharedPreferences = context.getSharedPreferences(SAVE, Context.MODE_PRIVATE)
        lastVersion = sharedPreferences.getInt("lastVersion", 0)
        crownScrollSpeed = sharedPreferences.getFloat("crownScrollSpeed", 1.5f)
        instantScroll = sharedPreferences.getBoolean("instantScroll", false)
    }

}