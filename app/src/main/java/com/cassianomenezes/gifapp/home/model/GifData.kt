package com.cassianomenezes.gifapp.home.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class GifData(@SerializedName("data") val data : List<Gif>) : Parcelable