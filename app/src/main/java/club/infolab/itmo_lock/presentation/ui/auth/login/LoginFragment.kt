package club.infolab.itmo_lock.presentation.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.databinding.FragmentLoginBinding
import club.infolab.itmo_lock.presentation.ui.auth.AuthViewModel
import club.infolab.itmo_lock.presentation.ui.auth.LoadStatus
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtons()
        initStatusObserver()
    }

    private fun initStatusObserver() {
        authViewModel.loginStatus.observe(viewLifecycleOwner) { status ->
            viewAccordingToStatus(status)
        }
    }

    private fun viewAccordingToStatus(status: LoadStatus?) {
        when (status) {
            is LoadStatus.Loading -> {
                binding.loadingBar.visibility = View.VISIBLE
            }
            is LoadStatus.Error -> {
                binding.loadingBar.visibility = View.GONE
                binding.passwordField.error = status.error.toString()
                binding.emailField.error = status.error.toString()
            }
            is LoadStatus.Success -> {
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
        authViewModel.login(
            binding.emailField.text.toString(),
            binding.passwordField.text?.toList() ?: arrayListOf()
        )
    }
}
