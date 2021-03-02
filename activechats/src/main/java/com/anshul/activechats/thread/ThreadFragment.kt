package com.anshul.activechats.thread

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anshul.activechats.R
import com.anshul.activechats.databinding.FragmentThreadsBinding

/**
 * @author anshulgoel
 * at 04/09/20, 8:04 PM
 * for ChatBook
 */
class ThreadFragment : Fragment() {
    private lateinit var viewModel: ThreadFragmentViewModel
//    private lateinit var router: IThreadFragmentNavigation


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentThreadsBinding.inflate(inflater, container, false)
        val viewModelFactory = ThreadViewModelFactory(application)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(ThreadFragmentViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rvThreads.adapter = ThreadsAdapter(ThreadsAdapter.OnClickListener {
            viewModel.displayThreadDetails(it)
        })
        viewModel.navigateToSelectedThread.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController()
                    .navigate(ThreadFragmentDirections.actionThreadFragmentToActiveChatFragment(it))
                viewModel.displayThreadDetailsComplete()
            }
        })
        return binding.root
    }

}