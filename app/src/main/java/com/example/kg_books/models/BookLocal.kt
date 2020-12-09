package com.example.kg_books.models

data class BookLocal(
    var id: String,
    var name: String?,
    var size: String?,
    var location: String,
    var extension: String?,
    var lastPage: Int = 0
) {
    fun getNotNullExtension(): String {
        if(extension == null){
            return location.takeLast(3)
        }else{
            return extension!!
        }
    }
}