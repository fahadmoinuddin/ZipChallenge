package com.zip.challenge.core.extensions

import androidx.recyclerview.widget.RecyclerView

//Adds scroll listener for recycler view
fun RecyclerView.doOnScroll(f: (Boolean) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            f(dy < 0)
        }
    })
}