package com.example.photogallerychallenge.data.model

data class SearchResult(
    val total: Int,
    val total_pages: Int,
    val results: List<Photo>
)