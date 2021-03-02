package com.anshul.login.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.anshul.customviews.LoaderUtil
import com.anshul.login.ILoginFragmentNavigation
import com.anshul.login.R
import com.anshul.login.databinding.FragmentLoginBinding
import com.anshul.login.viewmodel.LoginViewModel
import com.anshul.login.viewmodel.LoginViewModelFactory


/**
 * @author anshulgoel
 * at 05/09/20, 3:17 PM
 * for ChatBook
 */
class LoginFragment : Fragment() {

    private lateinit var router: ILoginFragmentNavigation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        val viewModel: LoginViewModel =
            ViewModelProvider(this, LoginViewModelFactory(application)).get(
                LoginViewModel::class.java
            )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.showProgressDialog.observe(viewLifecycleOwner, Observer {
            if (it) LoaderUtil.showLoaderDialog(requireNotNull(context)) else LoaderUtil.hideLoaderDialog()
        })
        viewModel.loginSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (::router.isInitialized) {
                    router.loginSuccessful()
                }
            }
        })
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        router = activity as ILoginFragmentNavigation
    }
}