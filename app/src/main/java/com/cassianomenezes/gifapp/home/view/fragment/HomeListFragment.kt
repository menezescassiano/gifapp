package com.cassianomenezes.gifapp.home.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cassianomenezes.gifapp.BR
import com.cassianomenezes.gifapp.R
import com.cassianomenezes.gifapp.extension.bindingContentView
import com.cassianomenezes.gifapp.extension.dismissKeyboard
import com.cassianomenezes.gifapp.extension.observe
import com.cassianomenezes.gifapp.extension.showToast
import com.cassianomenezes.gifapp.home.model.Gif
import com.cassianomenezes.gifapp.home.view.adapter.GifListAdapter
import com.cassianomenezes.gifapp.home.view.viewmodel.HomeListViewModel
import kotlinx.android.synthetic.main.fragment_home_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeListFragment : Fragment() {

    private val viewModel: HomeListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            observe(responseData) {
                setRecyclerView()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindingContentView(inflater, R.layout.fragment_home_list, container).apply {
            setVariable(BR.viewModel, viewModel)
            setVariable(BR.onSearchClick, View.OnClickListener {
                context?.dismissKeyboard(edit_query)
                viewModel.handleData()
            })
            setVariable(BR.onTryAgainClick, View.OnClickListener {
                viewModel.handleData()
            })
        }.root

    }

    private fun setRecyclerView() {
        val listAdapter = GifListAdapter(viewModel.list.data as ArrayList<Gif>)
        recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            //layoutManager = GridLayoutManager(this, 2)
        }

        listAdapter.apply {
            observe(selectedGif) {
                it?.title?.let { title -> context?.showToast(title) }
            }
            observe(saveGif) {
                it?.let {
                    viewModel.saveGif(it.id, it.images.originalDetail.url)
                }
            }
        }
    }
}