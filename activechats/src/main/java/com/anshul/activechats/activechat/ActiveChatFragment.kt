package com.anshul.activechats.activechat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anshul.activechats.R
import com.anshul.activechats.databinding.FragmentActiveChatBinding

/**
 * @author anshulgoel
 * at 04/09/20, 8:34 PM
 * for ChatBook
 */

class ActiveChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentActiveChatBinding.inflate(inflater, container, false)
        val threadID = ActiveChatFragmentArgs.fromBundle(requireArguments()).threadID

        val viewModelFactory = ActiveChatViewModelFactory(application, threadID)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(ActiveChatFragmentViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rvActiveChat.adapter = ActiveChatAdapter()


        viewModel.buttonClicked.observe(viewLifecycleOwner, Observer {
            if (!it) {
                return@Observer
            }
            binding.etChatbox.setText("")
            viewModel.buttonClickDone()
        })
        viewModel.messages.observe(viewLifecycleOwner, Observer {
            viewModel.messages.value?.size?.minus(1)?.let { it1 ->
                binding.rvActiveChat.scrollToPosition(
                    it1
                )
            }
        })
        return binding.root
    }

}