package com.cassianomenezes.gifapp.home.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cassianomenezes.gifapp.BR
import com.cassianomenezes.gifapp.R
import com.cassianomenezes.gifapp.extension.bindingContentView
import com.cassianomenezes.gifapp.extension.hasInternetConnection
import com.cassianomenezes.gifapp.extension.showToast
import com.cassianomenezes.gifapp.home.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        getData()
    }

    private fun setupBinding() {
        bindingContentView(R.layout.activity_main).apply {
            setVariable(BR.viewModel, viewModel)
            //setVariable(BR.onTryAgainClick, View.OnClickListener { onTryAgainClick() })
        }
    }

    private fun getData() {
        viewModel.run {
            when {
                hasInternetConnection() -> getData()
                else -> {
                    showToast(getString(R.string.internet_connection_warning))
                    //showTryAgain.set(true)
                }
            }
        }
    }
}