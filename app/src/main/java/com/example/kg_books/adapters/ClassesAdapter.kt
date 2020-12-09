package com.example.kg_books.adapters;

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.kg_books.R
import com.example.kg_books.activities.BooksActivity
import com.example.kg_books.models.School
import kotlinx.android.synthetic.main.item_class.view.*


class ClassesAdapter(private var myDataset: ArrayList<String>, var context: Context, val school: School)
    : RecyclerView.Adapter<ClassesAdapter.MyViewHolder>() {
    class MyViewHolder(val linearLayout: View) : RecyclerView.ViewHolder(linearLayout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {

        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_class, parent, false) as ConstraintLayout

        return MyViewHolder(linearLayout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = myDataset[position]
        holder.linearLayout.class_name.text = post
        holder.linearLayout.setOnClickListener {
            val i = Intent(context, BooksActivity::class.java)
            i.putExtra("school", school.fireBaseId)
            i.putExtra("class", (position+1).toString())
            context.startActivity(i)
        }
    }

    override fun getItemCount() = myDataset.size

    fun update(list : ArrayList<String>){
        myDataset = list
        notifyDataSetChanged()
    }
}