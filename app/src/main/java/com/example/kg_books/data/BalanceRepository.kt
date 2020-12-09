package com.example.kg_books.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson

class BalanceRepository(var context: Context) {
    private val gson = Gson()
    private val prefsNode = "coins"

    private val totalNode = "total"

    fun getCoins(): Int {
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE)
        return myPrefs.getInt(totalNode, 0)
    }

    @SuppressLint("CommitPrefEdits")
    fun setCoins(total: Int){
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.putInt(totalNode, total)
        myPrefs.apply()
    }

}