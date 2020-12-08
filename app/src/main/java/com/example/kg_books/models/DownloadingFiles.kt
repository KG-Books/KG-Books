package io.flaterlab.reader.models

data class DownloadingFiles(
    val id: Long,
    val firebaseId: String,
    val book: BookFirestore
) {

}