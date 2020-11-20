package com.cassiano.myapplication.utils

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import com.cassianomenezes.gifapp.R
import com.cassianomenezes.gifapp.extension.getSharedPreferences

class ResourceManager(val context: Context?) {

    fun getDrawable(id: Int): Drawable? = context?.let { DrawableUtils.getDrawable(it, id) }

    fun getShardPreferences(): SharedPreferences? = context?.getSharedPreferences(context.getString(R.string.app_shared_preferences))
}
