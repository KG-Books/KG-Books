package com.example.kg_books.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import com.example.kg_books.R
import com.example.kg_books.activities.ui.pdf.PdfActivity
import com.example.kg_books.data.Data
import com.example.kg_books.models.BookLocal
import kotlinx.android.synthetic.main.activity_view_book.*

import java.io.File

class ViewBookActivity : AppCompatActivity() {

    lateinit var book: BookLocal
    lateinit var cache: Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_book)

        val id = intent.getStringExtra("id")

        cache = Data(this)

        book = cache.findBook(id!!)!!

        bookTitle.text = book.name


        pdfView.fromFile(File(book.location)).pages(0).load()
        size.text = book.size
        lastPage.text = "${book.lastPage}"
        extension.text = book.getNotNullExtension()

        fab.setOnClickListener{
            val i = Intent(this, PdfActivity::class.java)
            i.putExtra("id", book.id)
            startActivityForResult(i, 1)
        }

        delete.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle(getString(R.string.delete_book))
                    .setMessage(getString(R.string.delete_book_dialog))
                    .setPositiveButton(getString(R.string.yes)
                    ) { dialog, _ ->
                        cache.removeBook(book)
                        File(book.location).delete()
                        onBackPressed()
                        dialog.dismiss()
                    }
                    .setNegativeButton(getString(R.string.no), null)
                    .show()
        }

        back_button.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val a = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val animSet = AnimationSet(false)
        animSet.addAnimation(a)
        pdfView.startAnimation(animSet)
    }
}