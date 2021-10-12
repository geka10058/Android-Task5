package com.androidTask5.imagesearchapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
@Parcelize
data class CatPhoto(
    val id: String,
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashUser
) : Parcelable {

    @Parcelize
    data class UnsplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
    ) : Parcelable

    @Parcelize
    data class UnsplashUser(
        val name: String,
        val username: String
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}*/

@Parcelize
data class CatPhoto(
    val id:String,
    val url: String,
    //val breeds: CatBreed
):Parcelable {

    /*@Parcelize
    data class CatBreed(
        val id: Int,
        val name: String
    ):Parcelable*/
}