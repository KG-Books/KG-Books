package com.example.kg_books.activities.ui.main

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.kg_books.R
import com.example.kg_books.activities.ChooseSchoolActivity
import com.example.kg_books.activities.ProfileActivity
import com.example.kg_books.data.Const
import com.example.kg_books.data.Data
import com.example.kg_books.models.BookLocal
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class MainActivity : AppCompatActivity() {

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val cache = Data(context)
            val d = cache.findDownloads(id)

            d?.apply {
                val book = BookLocal(this.book.id, this.book.name, this.book.size,
                    getExternalFilesDir(Const.booksFolder).toString()+"/"+this.book.id+"."+this.book.extension, this.book.extension)
                val file = File(book.location)
                if(file.exists()){
                    cache.addBooks(book)
                }
                cache.removeDownloads(id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener(fun(view: View) {
            startActivity(Intent(this, ChooseSchoolActivity::class.java))
        })

        if (!EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.dont_have_permission), 1, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.dont_have_permission), 1, Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        profile_button.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        MobileAds.initialize(this) {}
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about ->{
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onDownloadComplete)
    }
}