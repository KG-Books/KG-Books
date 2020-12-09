package com.example.kg_books.models

data class DownloadingFiles(
    val id: Long,
    val firebaseId: String,
    val book: BookFirestore
) {

}