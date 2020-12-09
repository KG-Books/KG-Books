package com.example.kg_books.adapters

import android.app.DownloadManager
import android.app.Service
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.kg_books.R
import com.example.kg_books.data.Const
import com.example.kg_books.data.Data
import com.example.kg_books.models.BookFirestore
import com.example.kg_books.models.DownloadingFiles
import kotlinx.android.synthetic.main.item_book_firestore.view.*


class BooksFirestoreAdapter(private var myDataset: ArrayList<BookFirestore>, var context: Context, val ids: ArrayList<String>)
    : RecyclerView.Adapter<BooksFirestoreAdapter.MyViewHolder>() {
    class MyViewHolder(val linearLayout: View) : RecyclerView.ViewHolder(linearLayout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): BooksFirestoreAdapter.MyViewHolder {

        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book_firestore, parent, false) as ConstraintLayout

        return MyViewHolder(linearLayout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val book: BookFirestore = myDataset[position]
        holder.linearLayout.book_name.text = book.name
        holder.linearLayout.books_size.text = book.size

        if(book.id in ids){
            holder.linearLayout.downloadButton.visibility = View.GONE
        }
        holder.linearLayout.downloadButton.setOnClickListener {
            Toast.makeText(context, "Загружаю ... ", Toast.LENGTH_LONG).show()
            holder.linearLayout.downloadButton.visibility = View.GONE
            val downloadManager: DownloadManager = context.getSystemService(Service.DOWNLOAD_SERVICE) as DownloadManager
            val uri = Uri.parse(book.location)
            val request = DownloadManager.Request(uri)
            request.setAllowedOverRoaming(false)
            request.setTitle(book.name)
            request.setDescription("Downloading: ${book.name} ${book.size}")
            request.setDestinationInExternalFilesDir(context, Const.booksFolder, "${book.id}.${book.extension}")
            val downloadReference = downloadManager.enqueue(request)
            Data(context).addDownloads(DownloadingFiles(downloadReference, book.id, book))

        }
    }

    override fun getItemCount() = myDataset.size

    fun update(list : ArrayList<BookFirestore>){
        myDataset = list
        notifyDataSetChanged()
    }
}