package com.cassianomenezes.gifapp.home.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class OriginalDetail(@SerializedName("url") val url: String) : Parcelable