package io.flaterlab.reader.activities.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.flaterlab.reader.R
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