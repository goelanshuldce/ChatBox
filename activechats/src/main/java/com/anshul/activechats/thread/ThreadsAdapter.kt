package com.anshul.activechats.thread

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anshul.activechats.databinding.ThreadViewItemBinding
import com.anshul.entities.domain.ChatThread

/**
 * @author anshulgoel
 * at 05/09/20, 5:37 PM
 * for ChatBook
 */
class ThreadsAdapter(val onClickListener: OnClickListener) :
    ListAdapter<ChatThread, ThreadsAdapter.ThreadMessageViewHolder>(DiffCallback) {

    /**
     * The ThreadMessageViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [ChatThread] information.
     */
    class ThreadMessageViewHolder(private var binding: ThreadViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(thread: ChatThread) {
            binding.thread = thread
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ThreadMessageViewHolder {
        return ThreadMessageViewHolder(ThreadViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ThreadMessageViewHolder, position: Int) {
        val thread = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(thread.threadID.toInt())
        }
        holder.bind(thread)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ChatThread]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ChatThread>() {
        override fun areItemsTheSame(oldItem: ChatThread, newItem: ChatThread): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatThread, newItem: ChatThread): Boolean {
            return oldItem.id == newItem.id
        }
    }


    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [ChatThread.threadID]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [ChatThread]
     */
    class OnClickListener(val clickListener: (threadID: Int) -> Unit) {
        fun onClick(threadID: Int) = clickListener(threadID)
    }
}