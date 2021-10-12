package com.androidTask5.imagesearchapp.api

import com.androidTask5.imagesearchapp.data.CatPhoto

data class CatResponse(
    val results: List<CatPhoto>
)