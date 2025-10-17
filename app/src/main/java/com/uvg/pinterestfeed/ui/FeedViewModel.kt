package com.uvg.pinterestfeed.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.uvg.pinterestfeed.data.PhotoRepository
import com.uvg.pinterestfeed.data.PhotoPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FeedViewModel(createSavedStateHandle: SavedStateHandle) : ViewModel() {

    private val repo = PhotoRepository()

    val feed = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 10,
            enablePlaceholders = false
        )
    ) { PhotoPagingSource(repo) }
        .flow
        .cachedIn(viewModelScope)


    private val _scrollIndex = MutableStateFlow(0)
    val scrollIndex = _scrollIndex.asStateFlow()

    private val _scrollOffset = MutableStateFlow(0)
    val scrollOffset = _scrollOffset.asStateFlow()

    fun saveScroll(index: Int, offset: Int) {
        _scrollIndex.value = index
        _scrollOffset.value = offset
    }
}
