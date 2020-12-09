package com.example.kg_books.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.kg_books.models.BookLocal
import com.example.kg_books.models.DownloadingFiles

import java.lang.reflect.Type

class Data(var context: Context){
    private val gson = Gson()
    private val prefsNode = "prefs"

    private val booksNode = "books"
    fun getBooks(): ArrayList<BookLocal>{
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE)
        val listType: Type = object : TypeToken<ArrayList<BookLocal>>() {}.type
        val listJson = myPrefs.getString(booksNode, "")

        if (listJson == ""){
            return arrayListOf()
        }

        return gson.fromJson(listJson, listType)
    }

    fun saveBooks(list: ArrayList<BookLocal>){
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.putString(booksNode, gson.toJson(list))
        myPrefs.apply()
    }

    fun removeBook(book: BookLocal){
        val a = getBooks()
        val new = arrayListOf<BookLocal>()
        a.forEach {
            if(it.id != book.id){
                new.add(it)
            }
        }
        saveBooks(new)
    }

    fun saveBook(book: BookLocal){
        val a = getBooks()
        val new = arrayListOf<BookLocal>()
        a.forEach {
            if(it.id != book.id){
                new.add(it)
            }else{
                new.add(book)
            }
        }
        saveBooks(new)
    }

    fun addBooks(file: BookLocal){
        val a = getBooks()
        a.forEach {
            if (it.id == file.id){
                return
            }
        }
        a.add(file)
        saveBooks(a)
    }

    fun findBook(id: String): BookLocal? {
        val a = getBooks()
        a.forEach {
            if (it.id == id){
                return it
            }
        }
        return null
    }

    private val downloadsNode = "downloads"
    fun getDownloads(): ArrayList<DownloadingFiles>{
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE)
        val listType: Type = object : TypeToken<ArrayList<DownloadingFiles>>() {}.type
        val listJson = myPrefs.getString(downloadsNode, "")
        if (listJson == ""){
            return arrayListOf()
        }
        return gson.fromJson(listJson, listType)
    }

    fun saveDownloads(list: ArrayList<DownloadingFiles>){
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.putString(downloadsNode, gson.toJson(list))
        myPrefs.apply()
    }

    fun addDownloads(file: DownloadingFiles){
        val a = getDownloads()
        a.add(file)
        saveDownloads(a)
    }

    fun removeDownloads(id: Long){
        val a = getDownloads()
        val new = arrayListOf<DownloadingFiles>()
        a.forEach {
            if(it.id != id){
                new.add(it)
            }
        }
        saveDownloads(new)
    }

    fun removeDownLoadsByFirebase(id: String){
        val a = getDownloads()
        val new = arrayListOf<DownloadingFiles>()
        a.forEach {
            if(it.firebaseId != id){
                new.add(it)
            }
        }
        saveDownloads(new)
    }

    fun findDownloads(id: Long): DownloadingFiles? {
        val a = getDownloads()
        a.forEach {
            if(it.id == id){
                return it
            }
        }
        return null
    }

    fun getAllIds(): ArrayList<String>{
        val a = getBooks()
        val b = getDownloads()
        val new = arrayListOf<String>()
        a.forEach {
            new.add(it.id)
        }
        b.forEach {
            new.add(it.firebaseId)
        }
        return new
    }
}