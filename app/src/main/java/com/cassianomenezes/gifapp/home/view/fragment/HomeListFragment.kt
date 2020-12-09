package com.cassianomenezes.gifapp.home.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cassianomenezes.gifapp.BR
import com.cassianomenezes.gifapp.BaseApplication
import com.cassianomenezes.gifapp.R
import com.cassianomenezes.gifapp.extension.bindingContentView
import com.cassianomenezes.gifapp.extension.dismissKeyboard
import com.cassianomenezes.gifapp.extension.observe
import com.cassianomenezes.gifapp.extension.showToast
import com.cassianomenezes.gifapp.home.database.GifObject
import com.cassianomenezes.gifapp.home.model.Gif
import com.cassianomenezes.gifapp.home.view.adapter.GifListAdapter
import com.cassianomenezes.gifapp.home.view.viewmodel.HomeListViewModel
import kotlinx.android.synthetic.main.fragment_home_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HomeListFragment : Fragment() {

    private val viewModel: HomeListViewModel by viewModel{ parametersOf((activity?.application as BaseApplication).gifRepositoryImpl)}

    private var initialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            observe(responseData) {
                handleList()
                initialized = true
            }
            observe(listData) {
                it?.let {
                    setRecyclerView(it)
                }
            }
            lifecycle.addObserver(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindingContentView(inflater, R.layout.fragment_home_list, container).apply {
            setVariable(BR.viewModel, viewModel)
            setVariable(BR.onSearchClick, View.OnClickListener {
                context?.dismissKeyboard(edit_query)
                viewModel.handleData()
            })
        }.root

    }

    private fun handleList() {
        viewModel.list?.let {
            viewModel.handleList(it.data as ArrayList<Gif>)
        }
    }

    private fun setRecyclerView(arrayList: ArrayList<GifObject>) {
        val listAdapter = GifListAdapter(arrayList)
        recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            listAdapter.apply {
                observe(selectedGif) { obj ->
                    obj?.title?.let { title -> context?.showToast(title) }
                }
                observe(saveGif) { obj ->
                    obj?.let {
                        viewModel.gifCrud(GifObject(obj.id, obj.title, obj.url, obj.added))
                    }
                }
            }
        }
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible && initialized) {
            handleList()
        }
    }
}