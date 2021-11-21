package com.androidTask5.imagesearchapp.data

import android.util.Log
import androidx.paging.PagingSource
import com.androidTask5.imagesearchapp.api.CatApi

private const val UNSPLASH_STARTING_PAGE_INDEX = 1
private const val CATS_ORDER = "ASC"

class CatPagingSource(
    private val catApi: CatApi
) : PagingSource<Int, CatPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = catApi.searchPhotos(CATS_ORDER,position, params.loadSize)
            Log.d("AppDebug", "$response")

            LoadResult.Page(
                data = response,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            Log.d("AppDebug", exception.message.toString())
            LoadResult.Error(exception)
        }
    }
}