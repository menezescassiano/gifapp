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
            setVariable(BR.onSearchClick, View.OnClickListener { viewModel.getData() })
        }.root

    }

    private fun setRecyclerView() {
        val listAdapter = GifListAdapter(viewModel.list.data as ArrayList<Gif>)
        recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        listAdapter.selectedRecipe.observe(this@HomeListFragment, {
            context?.let {
                it.showToast("aeaeae")
            }
            /*Intent(this, RecipeDetailActivity::class.java).apply {
                this.putExtra(BUNDLE_RECIPE, it)
                startActivity(this)
            }*/
        })
    }
}