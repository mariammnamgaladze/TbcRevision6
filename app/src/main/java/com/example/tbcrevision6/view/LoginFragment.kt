package com.example.tbcrevision6.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tbcrevision6.BaseFragment
import com.example.tbcrevision6.R
import com.example.tbcrevision6.databinding.FragmentLoginBinding
import com.example.tbcrevision6.model.LoginForm
import com.example.tbcrevision6.model.UserInfo
import com.example.tbcrevision6.utils.FragmentResUtils
import com.example.tbcrevision6.utils.PreferenceKeys
import com.example.tbcrevision6.utils.ResponseHandler
import com.example.tbcrevision6.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun viewCreated() {

        checkSession()

        fragmentResultListener()

        onClickListeners()

    }

    private fun onClickListeners() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        binding.btnLogin.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getLoginFlow(getUserInfo()).collect {
                    when(it) {
                        is ResponseHandler.Success<*> -> {
                            if (binding.cbRemember.isChecked) {
                                viewModel.save(PreferenceKeys.TOKEN, (it.result as LoginForm).token ?: "")
                            } else {
                                findNavController().navigate(
                                    LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                                        token = (it.result as LoginForm).token ?: ""
                                    )
                                )
                            }
                        }
                        is ResponseHandler.Error -> {
                            Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                        }
                        is ResponseHandler.Loader -> {
                            binding.progressBar.isVisible = it.isLoading
                        }
                    }
                }
            }
        }
    }

    private fun checkSession() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPreferences().collect {
                if (it.contains(stringPreferencesKey(PreferenceKeys.TOKEN))) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                        token = it[stringPreferencesKey(PreferenceKeys.TOKEN)] ?: "No Data"
                    ))
                }
            }
        }
    }

    private fun fragmentResultListener() {
        setFragmentResultListener(FragmentResUtils.AUTH_KEY) { _, bundle ->
            binding.etEmail.setText(bundle.getString(FragmentResUtils.EMAIL, "No Value"))
            binding.etPassword.setText(bundle.getString(FragmentResUtils.PASSWORD, "No Value"))
        }
    }

    private fun getUserInfo() = UserInfo(
        binding.etEmail.text.toString(),
        binding.etPassword.text.toString()
    )

}