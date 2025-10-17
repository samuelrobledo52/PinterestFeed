package com.uvg.pinterestfeed.data

object FakeApi {
    fun getPhotos(page: Int, pageSize: Int): List<Photo> {
        val start = page * pageSize
        return (start until start + pageSize).map { i ->
            val w = listOf(450, 500, 550, 600, 640).random()
            val h = listOf(700, 800, 900, 960, 1024).random()
            val url = "https://picsum.photos/seed/$i/$w/$h"
            Photo(
                id = i.toString(),
                url = url,
                title = "Photo #$i",
                width = w,
                height = h
            )
        }
    }
}

