package com.uvg.pinterestfeed.data

data class Photo(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val title: String
) {
    val aspectRatio: Float get() = width.toFloat() / height.toFloat()
}
