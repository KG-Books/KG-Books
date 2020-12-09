package com.example.kg_books.activities.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kg_books.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        help.setOnClickListener {
            startActivity(Intent(this, HelpActivity::class.java))
        }
        back_button.setOnClickListener {
            onBackPressed()
        }
    }
}