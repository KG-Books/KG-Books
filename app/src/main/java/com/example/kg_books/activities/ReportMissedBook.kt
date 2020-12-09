package com.example.kg_books.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kg_books.models.Message
import com.example.kg_books.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.kg_books.dialogs.PartyDialog
import kotlinx.android.synthetic.main.activity_report_missed_book.*
import java.util.*

class ReportMissedBook : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_missed_book)
        val db = Firebase.firestore

        back_button.setOnClickListener {
            onBackPressed()
        }

        send.setOnClickListener {
            val text = message.text.toString()

            if(text !== ""){
                progressBar.visibility = View.VISIBLE
                val message = Message(text, Date().time)
                db.collection("messages").add(message).addOnSuccessListener {
                    progressBar.visibility = View.GONE
                    val partyDialog = PartyDialog(this,
                            getString(R.string.message_saved_on_servers ),
                            getString(R.string.message_thank),
                            View.OnClickListener {
                                onBackPressed()
                            })
                    partyDialog.show()
                }.addOnFailureListener {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.message_send_error), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}