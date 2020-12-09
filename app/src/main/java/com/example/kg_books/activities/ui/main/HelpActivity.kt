package com.example.kg_books.activities.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kg_books.R
import kotlinx.android.synthetic.main.activity_help.*

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        back_button.setOnClickListener {
            onBackPressed()
        }
    }
}