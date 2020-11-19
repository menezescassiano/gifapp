package com.cassiano.myapplication.utils

import android.content.Context
import android.graphics.drawable.Drawable

class ResourceManager(val context: Context?) {

    fun getDrawable(id: Int): Drawable? = context?.let { DrawableUtils.getDrawable(it, id) }
}
