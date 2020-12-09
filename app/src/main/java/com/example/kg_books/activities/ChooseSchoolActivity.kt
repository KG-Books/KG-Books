package com.example.kg_books.activities

import android.os.Bundle

import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.kg_books.R
import com.example.kg_books.activities.ui.add.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_choose_school.*

class ChooseSchoolActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_school)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        back_button.setOnClickListener {
            onBackPressed()
        }
    }
}