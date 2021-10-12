package com.androidTask5.imagesearchapp.data

import androidx.paging.Pager
import androidx.paging.PagingSource
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.androidTask5.imagesearchapp.api.CatApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatRepository @Inject constructor(private val catApi: CatApi) {

    fun getSearchResults() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CatPagingSource(catApi) }
        ).liveData
}