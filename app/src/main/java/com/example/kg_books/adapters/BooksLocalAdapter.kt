package com.example.kg_books.adapters

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.example.kg_books.R
import com.example.kg_books.activities.ViewBookActivity
import com.example.kg_books.models.BookLocal
import kotlinx.android.synthetic.main.activity_pdf.view.*
import kotlinx.android.synthetic.main.item_book_firestore.view.*

class BooksLocalAdapter(private var myDataset: ArrayList<BookLocal>, var parent: Fragment)
    : RecyclerView.Adapter<BooksLocalAdapter.MyViewHolder>() {
    val AD_TYPE = 1
    val ELSE_TYPE = 0
    class MyViewHolder(val linearLayout: View) : RecyclerView.ViewHolder(linearLayout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): BooksLocalAdapter.MyViewHolder {

        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book_local, parent, false) as LinearLayout

        if(viewType == AD_TYPE){
            linearLayout.adView.visibility = View.VISIBLE
            val adRequest = AdRequest.Builder().build()
            linearLayout.adView.loadAd(adRequest)
        }

        return MyViewHolder(linearLayout)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val book: BookLocal = myDataset[position]
        holder.linearLayout.book_name.text = book.name
        holder.linearLayout.books_size.text = book.size

        holder.linearLayout.setOnClickListener {
            val i = Intent(parent.requireContext(), ViewBookActivity::class.java)
            i.putExtra("id", book.id)

            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    parent.requireActivity(),
                    androidx.core.util.Pair.create<View, String>(
                            holder.linearLayout.book_name,
                            parent.getString(R.string.book_name)),
                    androidx.core.util.Pair.create<View, String>(
                            holder.linearLayout.books_size,
                            parent.getString(R.string.books_size)),
                    androidx.core.util.Pair.create<View, String>(
                            holder.linearLayout.pdfView,
                            parent.getString(R.string.book_view))
            )

            parent.requireContext().startActivity(i, activityOptions.toBundle())
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position % 10 == 0 && position != 0 || position == 3)
            return AD_TYPE
        return ELSE_TYPE
    }

    override fun getItemCount() = myDataset.size

    fun update(list : ArrayList<BookLocal>){
        myDataset = list
        notifyDataSetChanged()
    }
}