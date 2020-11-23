package com.cassianomenezes.gifapp.home.view.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cassiano.myapplication.utils.DrawableUtils
import com.cassianomenezes.gifapp.BR
import com.cassianomenezes.gifapp.R
import com.cassianomenezes.gifapp.home.database.GifObject

class GifViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    val context: Context = binding.root.context

    fun bind(item: GifObject) {

        this.binding.apply {
            item.run {
                setVariable(BR.title, title)
                setVariable(BR.imageURL, url)
                setIcon(item, item.added)
            }
            executePendingBindings()
        }
    }

    private fun setIcon(item: GifObject, contains: Boolean = false) {
        this.binding.apply {
            item.run {
                when {
                    contains -> setVariable(BR.favIcon, DrawableUtils.getDrawable(context, R.drawable.ic_fav))
                    else -> setVariable(BR.favIcon, DrawableUtils.getDrawable(context, R.drawable.ic_unfav))
                }
            }
        }
    }
}