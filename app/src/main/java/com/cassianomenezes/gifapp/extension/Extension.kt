package com.cassianomenezes.gifapp.extension

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun AppCompatActivity.bindingContentView(layout: Int): ViewDataBinding {
    return DataBindingUtil.setContentView(this, layout)
}

fun Fragment.bindingContentView(inflate: LayoutInflater, layout: Int, viewGroup: ViewGroup?): ViewDataBinding {
    return DataBindingUtil.inflate(inflate, layout, viewGroup, false)
}

fun <T> Fragment.bindingContentView(inflate: LayoutInflater, layout: Int, viewGroup: ViewGroup?): T {
    return DataBindingUtil.inflate<ViewDataBinding>(inflate, layout, viewGroup, false) as T
}


fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, expression: (T?) -> Unit) {
    liveData.observe(this, Observer(expression))
}

fun Context.getSharedPreferences(name: String): SharedPreferences {
    return getSharedPreferences(name, Context.MODE_PRIVATE)
}

fun Context.hasInternetConnection(): Boolean {
    return (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo?.isConnected
        ?: false
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.dismissKeyboard(editText: EditText) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}