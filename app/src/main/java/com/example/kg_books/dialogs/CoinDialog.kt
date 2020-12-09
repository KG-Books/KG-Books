package com.example.kg_books.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.kg_books.R

import kotlinx.android.synthetic.main.dialog_party.*

class CoinDialog(context: Context, private val title: String) : Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_coins)
        dialog_title.text = title
        ok_button.setOnClickListener{
            this.dismiss()
        }
    }
}