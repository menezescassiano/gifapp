package com.cassianomenezes.gifapp.home.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.cassianomenezes.gifapp.BR
import com.cassianomenezes.gifapp.BaseApplication
import com.cassianomenezes.gifapp.R
import com.cassianomenezes.gifapp.extension.bindingContentView
import com.cassianomenezes.gifapp.extension.observe
import com.cassianomenezes.gifapp.extension.showToast
import com.cassianomenezes.gifapp.home.database.GifObject
import com.cassianomenezes.gifapp.home.view.adapter.GifListAdapter
import com.cassianomenezes.gifapp.home.view.viewmodel.FavListViewModel
import kotlinx.android.synthetic.main.fragment_home_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavListFragment: Fragment() {

    private val viewModel: FavListViewModel by viewModel{ parametersOf((activity?.application as BaseApplication).gifRepositoryImpl)}

    private lateinit var listAdapter: GifListAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return bindingContentView(inflater, R.layout.fragment_favorite_list, container).apply {
            setVariable(BR.viewModel, viewModel)
            setVariable(BR.onTryAgainClick, View.OnClickListener {
                viewModel.getAllElements()
            })
        }.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            observe(listData) {
                it?.let {
                    setRecyclerView(it)
                }
            }
            observe(onDeleteGif) {
                listAdapter.notifyDataSetChanged()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {
            viewModel.getAllElements()
        }
    }

    private fun setRecyclerView(arrayList: ArrayList<GifObject>) {
        listAdapter = GifListAdapter(arrayList, true)
        recyclerView.apply {
            adapter = listAdapter
            layoutManager = GridLayoutManager(context, 2)

            listAdapter.apply {
                observe(selectedGif) { obj ->
                    obj?.title?.let { title -> context?.showToast(title) }
                }
                observe(saveGif) { obj ->
                    obj?.let {
                        viewModel.deleteGif(obj)
                    }
                }
            }
        }
    }
}