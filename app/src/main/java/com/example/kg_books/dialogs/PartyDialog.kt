package com.example.kg_books.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.kg_books.R

import kotlinx.android.synthetic.main.dialog_party.*

class PartyDialog(context: Context,
                  private val  message: String,
                  private val title: String,
                  val listener: View.OnClickListener) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_party)
        dialog_title.text = title
        dialog_body.text = message
        ok_button.setOnClickListener(listener)
    }
}