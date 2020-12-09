package com.example.kg_books.activities.ui.add

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.kg_books.models.School


private val tabs_data = arrayListOf(
    School("Русская школа", 0, "c3lE9hmMIhzHHQialuzf"),
    School("O'zbek maktab", 1, "iBgDyUAymOALMqUkzjud"),
    School("Кыргыз мектеп", 2, "rkS489ydesPagS2lr3GX")
)
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(tabs_data[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabs_data[position].tabName
    }

    override fun getCount(): Int {

        return 3
    }
}