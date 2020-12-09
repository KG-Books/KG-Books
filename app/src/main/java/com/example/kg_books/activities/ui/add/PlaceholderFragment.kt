package com.example.kg_books.activities.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kg_books.R
import com.example.kg_books.adapters.ClassesAdapter
import com.example.kg_books.models.School

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment(val school: School) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ClassesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_choose_school, container, false)

        viewManager = LinearLayoutManager(context)
        viewAdapter = ClassesAdapter(
            arrayListOf("1 класс", "2 класс", "3 класс", "4 класс", "5 класс",
                "6 класс", "7 класс", "8 класс", "9 класс", "10 класс", "11 класс"),
            requireContext(), school)

        recyclerView = root.findViewById<RecyclerView>(R.id.posts_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return root
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(schoolInfo: School): PlaceholderFragment {
            return PlaceholderFragment(schoolInfo)
        }
    }
}