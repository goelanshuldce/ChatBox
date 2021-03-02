package com.anshul.activechats.thread

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anshul.entities.domain.ChatThread

/**
 * @author anshulgoel
 * at 05/09/20, 9:54 PM
 * for ChatBook
 */

/**
 * When there is no [ChatThread] data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ChatThread>?) {
    val adapter = recyclerView.adapter as ThreadsAdapter
    adapter.submitList(data)
}