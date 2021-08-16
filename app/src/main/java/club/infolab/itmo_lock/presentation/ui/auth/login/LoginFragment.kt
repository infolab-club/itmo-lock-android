package club.infolab.itmo_lock.presentation.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.databinding.LoginFragmentBinding
import club.infolab.itmo_lock.presentation.ui.auth.LoginStatus
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private lateinit var binding: LoginFragmentBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding
            .bind(inflater.inflate(R.layout.login_fragment, container, false))
        initButtons()
        initStatusObserver()
        return binding.root
    }

    private fun initStatusObserver() {
        loginViewModel.loginStatus.observe(viewLifecycleOwner) { status ->
            viewAccordingToStatus(status)
        }
    }

    private fun viewAccordingToStatus(status: LoginStatus?) {
        when (status) {
            is LoginStatus.Loading -> {
                binding.loadingBar.visibility = View.VISIBLE
            }
            is LoginStatus.Error -> {
                binding.loadingBar.visibility = View.GONE
                binding.passwordField.error = status.error.toString()
                binding.emailField.error = status.error.toString()
            }
            is LoginStatus.Success -> {
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }
        }
    }

    private fun initButtons() {
        binding.loginButton.setOnClickListener {
            login()
        }

        binding.regTextButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun login() {
        loginViewModel.loginWithData(
            binding.emailField.text.toString(),
            binding.passwordField.text?.toList() ?: arrayListOf()
        )
    }
}