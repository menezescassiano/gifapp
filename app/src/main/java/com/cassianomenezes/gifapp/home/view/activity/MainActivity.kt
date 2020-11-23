package com.cassianomenezes.gifapp.home.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cassianomenezes.gifapp.R
import com.cassianomenezes.gifapp.extension.bindingContentView
import com.cassianomenezes.gifapp.home.database.AppDatabase
import com.cassianomenezes.gifapp.home.database.GifRepository
import com.cassianomenezes.gifapp.home.database.GifRepositoryImpl
import com.cassianomenezes.gifapp.home.view.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    lateinit var mAdapter: PagerAdapter

    lateinit var gifRepositoryImpl: GifRepository
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()

        setupTabLayout()
        initDB()
    }

    private fun initDB() {
        db = AppDatabase.invoke(this)
        gifRepositoryImpl = GifRepositoryImpl(db.gifDao())
    }

    private fun setupBinding() {
        bindingContentView(R.layout.activity_main)
    }

    private fun setupTabLayout() {
        val gifListTab = tabLayout.newTab()
        gifListTab.text = getString(R.string.gif_list_tab_title)
        val favListTab = tabLayout.newTab()
        favListTab.text = getString(R.string.fav_gif_list_tab_title)

        tabLayout.run {
            addTab(gifListTab)
            addTab(favListTab)
            tabGravity = TabLayout.GRAVITY_FILL
            addOnTabSelectedListener(this@MainActivity)
        }

        mAdapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount)

        viewPager.run {
            adapter = mAdapter
            addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
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