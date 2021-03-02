package com.anshul.activechats.activechat

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anshul.entities.domain.ChatMessage

/**
 * @author anshulgoel
 * at 06/09/20, 1:29 AM
 * for ChatBook
 */

/**
 * When there is no [ChatMessage] data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listChatData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ChatMessage>?) {
    val adapter = recyclerView.adapter as ActiveChatAdapter
    adapter.submitList(data)
}