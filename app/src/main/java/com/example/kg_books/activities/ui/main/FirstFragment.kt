package com.example.kg_books.activities.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kg_books.R
import com.example.kg_books.adapters.BooksLocalAdapter
import com.example.kg_books.data.Data
import kotlinx.android.synthetic.main.fragment_first.view.*

class FirstFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: BooksLocalAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var root: View
    lateinit var cache: Data

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_first, container, false)

        cache = Data(requireContext())

        viewManager = LinearLayoutManager(requireContext())
        viewAdapter = BooksLocalAdapter(cache.getBooks() , this)

        recyclerView = root.findViewById<RecyclerView>(R.id.booksRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        if(viewAdapter.itemCount == 0){
            root.image_not_found.visibility = View.VISIBLE
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        viewAdapter.update(cache.getBooks())
        if(viewAdapter.itemCount == 0){
            root.image_not_found.visibility = View.VISIBLE
        }else{
            root.image_not_found.visibility = View.GONE
        }
    }
}