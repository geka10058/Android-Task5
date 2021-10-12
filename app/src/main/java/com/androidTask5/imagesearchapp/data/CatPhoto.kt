package com.androidTask5.imagesearchapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CatPhoto(
    val id:String,
    val url: String,
    val breeds: List<CatBreeds>
):Parcelable {

    @Parcelize
    data class CatBreeds(
        val id: Int,
        val name: String
    ):Parcelable
}