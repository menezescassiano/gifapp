package com.cassianomenezes.gifapp.home.view.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cassianomenezes.gifapp.home.model.Gif
import com.cassianomenezes.gifapp.BR

class GifViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Gif) {

        this.binding.apply {

            item.run {
                setVariable(BR.title, title)
                setVariable(BR.imageURL, images.originalDetail.url)
            }
            executePendingBindings()
        }
    }
}