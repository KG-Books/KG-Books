package com.example.kg_books.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.kg_books.R
import com.example.kg_books.adapters.BooksFirestoreAdapter
import com.example.kg_books.models.BookFirestore
import com.example.kg_books.data.Data
import kotlinx.android.synthetic.main.activity_books.*

class BooksActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: BooksFirestoreAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        val schoolId = intent.getStringExtra("school")
        val classId = intent.getStringExtra("class")

        activityTitle.text = "Учебники: $classId класс"

        viewManager = LinearLayoutManager(this)
        viewAdapter = BooksFirestoreAdapter(arrayListOf() , this, Data(this).getAllIds())

        recyclerView = findViewById<RecyclerView>(R.id.booksRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        back_button.setOnClickListener {
            onBackPressed()
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setIcon(R.drawable.ic_back_button)
        }

        val db = Firebase.firestore
        progressBar.visibility = View.VISIBLE
        db.collection("shcools/$schoolId/classes/$classId/books")
                .get()
                .addOnSuccessListener {
                    progressBar.visibility = View.GONE
                    val new = arrayListOf<BookFirestore>()
                    it.documents.forEach {doc ->
                        if (doc.contains("isVisible") && doc.get("isVisible") as Boolean){
                            val book = BookFirestore(doc.id,null, null, null)
                            if (doc.contains("size")) {book.size = doc.get("size").toString()}
                            if (doc.contains("name")) {book.name = doc.get("name").toString()}
                            if (doc.contains("location")) {book.location = doc.get("location").toString()}
                            if (doc.contains("extension")) {book.extension = doc.get("extension").toString()}
                            book.isVisible = doc.get("isVisible") as Boolean
                            new.add(book)
                        }
                    }
                    booksCount.text = new.size.toString()
                    viewAdapter.update(new)
                    if(new.size == 0){
                        image_not_found.visibility = View.VISIBLE
                    }else{
                        image_not_found.visibility = View.GONE
                    }
                }
                .addOnFailureListener {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Ошибка загрузки", Toast.LENGTH_LONG).show()
                    image_not_found.visibility = View.VISIBLE
                    Log.d("error", it.toString())
                }

        didn_found.setOnClickListener{
            startActivity(Intent(this, ReportMissedBook::class.java))
        }
    }
}