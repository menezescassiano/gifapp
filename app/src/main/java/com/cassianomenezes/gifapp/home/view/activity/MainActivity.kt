package com.cassianomenezes.gifapp.home.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cassianomenezes.gifapp.BR
import com.cassianomenezes.gifapp.R
import com.cassianomenezes.gifapp.extension.bindingContentView
import com.cassianomenezes.gifapp.home.view.adapter.PagerAdapter
import com.cassianomenezes.gifapp.home.view.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private val viewModel: MainViewModel by viewModel()
    lateinit var adapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()

        val myTab1 = tabLayout.newTab()
        myTab1.text = "Tab 1"
        val myTab2 = tabLayout.newTab()
        myTab2.text = "Tab 2"
        tabLayout.addTab(myTab1)
        tabLayout.addTab(myTab2)
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        adapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(this)
    }

    private fun setupBinding() {
        bindingContentView(R.layout.activity_main).apply {
            setVariable(BR.viewModel, viewModel)
            //setVariable(BR.onTryAgainClick, View.OnClickListener { onTryAgainClick() })
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.position?.let { viewPager.currentItem = it }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        //does nothing
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        //does nothing
    }
}