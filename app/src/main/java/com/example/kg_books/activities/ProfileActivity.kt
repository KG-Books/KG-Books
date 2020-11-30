package com.example.kg_books.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kg_books.R
import io.flaterlab.reader.R
import io.flaterlab.reader.activities.ui.main.AboutActivity
import io.flaterlab.reader.data.BalanceRepository
import io.flaterlab.reader.data.Data
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    lateinit var balance: BalanceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        back_button.setOnClickListener {
            onBackPressed()
        }

        balance = BalanceRepository(this)

        balanceCoins.text = balance.getCoins().toString()

        about_application.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }


        books_amount.text = Data(this).getBooks().size.toString()
    }
}