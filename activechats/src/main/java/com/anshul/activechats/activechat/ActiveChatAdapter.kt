package com.anshul.activechats.activechat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anshul.activechats.databinding.ItemMessageReceivedBinding
import com.anshul.activechats.databinding.ItemMessageSentBinding
import com.anshul.entities.domain.ChatMessage
import com.anshul.entities.domain.ChatThread

/**
 * @author anshulgoel
 * at 06/09/20, 1:30 AM
 * for ChatBook
 */

interface IActiveChatViewHolder {
    fun bind(message: ChatMessage)
}

class ActiveChatAdapter : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(DiffCallback) {
    enum class ViewType {
        SENDER,
        RECEIVER
    }

    /**
     * The [RecyclerView.ViewHolder] constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [ChatMessage] information.
     */
    class SenderViewHolder(private var binding: ItemMessageSentBinding) :
        RecyclerView.ViewHolder(binding.root), IActiveChatViewHolder {
        override fun bind(message: ChatMessage) {
            binding.message = message
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * The [RecyclerView.ViewHolder] constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [ChatMessage] information.
     */
    class ReceiverViewHolder(private var binding: ItemMessageReceivedBinding) :
        RecyclerView.ViewHolder(binding.root), IActiveChatViewHolder {
        override fun bind(message: ChatMessage) {
            binding.message = message
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.RECEIVER.ordinal -> ReceiverViewHolder(
                ItemMessageReceivedBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
            else -> SenderViewHolder(ItemMessageSentBinding.inflate(LayoutInflater.from(parent.context)))
        }
    }

    // Normally we avoid empty string checks
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).agentID) {
            "" -> {
                ViewType.RECEIVER.ordinal
            }
            else -> {
                ViewType.SENDER.ordinal
            }
        }
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     * We can expose them using a generic binder interface
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val thread = getItem(position)
        (holder as IActiveChatViewHolder).bind(thread)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ChatThread]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem.id == newItem.id
        }
    }


}