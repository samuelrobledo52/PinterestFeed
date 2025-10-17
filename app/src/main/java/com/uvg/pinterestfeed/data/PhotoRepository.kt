package com.uvg.pinterestfeed.data

class PhotoRepository {
    suspend fun fetchPhotos(page: Int, pageSize: Int): List<Photo> {
        return FakeApi.getPhotos(page, pageSize)
    }
}
