package com.cassianomenezes.gifapp.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.cassianomenezes.gifapp.R
import com.cassianomenezes.gifapp.databinding.LayoutGifListItemBinding
import com.cassianomenezes.gifapp.home.model.Gif

class GifListAdapter(private val list: ArrayList<Gif>) : RecyclerView.Adapter<GifViewHolder>() {

    val selectedGif: MutableLiveData<Gif> = MutableLiveData()
    val saveGif: MutableLiveData<Gif> = MutableLiveData()
    lateinit var binding: LayoutGifListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false) as LayoutGifListItemBinding
        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val item = list[position]

        holder.apply {
            bind(item)

            itemView.setOnClickListener {
                selectedGif.value = item
            }

            itemView.setOnLongClickListener {
                saveGif.value = item
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.layout_gif_list_item
    }
}