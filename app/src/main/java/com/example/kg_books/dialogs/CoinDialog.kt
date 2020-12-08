package io.flaterlab.reader.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import io.flaterlab.reader.R

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