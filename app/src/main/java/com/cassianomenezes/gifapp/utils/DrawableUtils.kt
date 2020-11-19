package com.cassiano.myapplication.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

object DrawableUtils {

    fun getDrawable(context: Context, @DrawableRes drawableRes: Int): Drawable? {
        return try {
            ContextCompat.getDrawable(context, drawableRes)
                ?: VectorDrawableCompat.create(context.resources, drawableRes, null)
        } catch (e: Resources.NotFoundException) {
            null
        }
    }
}
