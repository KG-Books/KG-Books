package io.flaterlab.reader.models

data class BookFirestore(
    var id: String,
    var name: String?,
    var size: String?,
    var location: String?,
    var extension: String = "pdf",
    var isVisible: Boolean = false
) {
}