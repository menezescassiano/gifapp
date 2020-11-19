package com.cassianomenezes.gifapp.home.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Gif(@SerializedName("id") val id : String,
          @SerializedName("title") val title: String,
          @SerializedName("images") val images : Original
) : Parcelable