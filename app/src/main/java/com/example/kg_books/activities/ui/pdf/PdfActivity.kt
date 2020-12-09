package com.example.kg_books.activities.ui.pdf

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.gms.ads.AdRequest
import com.example.kg_books.R
import com.example.kg_books.data.BalanceRepository
import com.example.kg_books.data.Data
import com.example.kg_books.dialogs.CoinDialog
import com.example.kg_books.models.BookLocal
import kotlinx.android.synthetic.main.activity_pdf.*
import java.io.File

class PdfActivity : AppCompatActivity() {
    lateinit var viewModel: PdfViewModel

    lateinit var cache: Data
    lateinit var balance: BalanceRepository
    lateinit var file: BookLocal
    lateinit var coinsThread: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)
        viewModel = ViewModelProvider(this).get(PdfViewModel::class.java)
        val id = intent.getStringExtra("id")
        cache = Data(this)
        file = cache.findBook(id!!)!!
        val name = file.location
        viewModel.page = file.lastPage

        coinsThread = Thread{
            try {
                while (true){
                    Thread.sleep(120_000)
                    viewModel.coins++
                }
            }catch (e: InterruptedException){

            }
        }

        coinsThread.start()

        coins.setOnClickListener {
            CoinDialog(this, viewModel.coins.toString()).show()
        }

        balance = BalanceRepository(this)
        viewModel.coins = balance.getCoins()

        val pdfView = findViewById<PDFView>(R.id.pdfView)
            .fromFile(File(name))
            .onPageChange { page, pageCount ->
                viewModel.page = page
                showCount(page, pageCount)
            }
            .defaultPage(viewModel.page)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        showCount(viewModel.page, adView.childCount)
        pdfView.load()
    }

    @SuppressLint("SetTextI18n")
    fun showCount(page: Int, pageCount: Int){
        textCount.text = "$page / $pageCount"
    }

    override fun onDestroy() {
        super.onDestroy()
        file.lastPage = viewModel.page
        cache.saveBook(file)
        balance.setCoins(viewModel.coins)
        coinsThread.interrupt()
    }

}

