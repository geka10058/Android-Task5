package com.androidTask5.imagesearchapp.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.androidTask5.imagesearchapp.data.CatRepository

class GalleryViewModel @ViewModelInject constructor(
    private val repository: CatRepository
) : ViewModel() {

    val photos = repository.getSearchResults().cachedIn(viewModelScope)
}