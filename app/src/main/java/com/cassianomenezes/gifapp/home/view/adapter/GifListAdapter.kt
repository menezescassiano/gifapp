package com.cassianomenezes.gifapp.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.cassianomenezes.gifapp.R
import com.cassianomenezes.gifapp.databinding.LayoutGifGridItemBinding
import com.cassianomenezes.gifapp.databinding.LayoutGifListItemBinding
import com.cassianomenezes.gifapp.home.database.GifObject
import kotlinx.android.synthetic.main.layout_gif_list_item.view.*

class GifListAdapter(private val list: ArrayList<GifObject>, private val isGridLayout: Boolean = false) : RecyclerView.Adapter<GifViewHolder>() {

    val selectedGif: MutableLiveData<GifObject> = MutableLiveData()
    val saveGif: MutableLiveData<GifObject> = MutableLiveData()
    lateinit var binding: ViewDataBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = if (isGridLayout) {
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false) as LayoutGifGridItemBinding
        } else {
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false) as LayoutGifListItemBinding
        }

        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val item = list[position]

        holder.apply {
            bind(item)

            itemView.run {
                setOnClickListener { selectedGif.value = item }
                favIcon.setOnClickListener { saveGif.value = item }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isGridLayout -> R.layout.layout_gif_grid_item
            else -> R.layout.layout_gif_list_item
        }
    }
}