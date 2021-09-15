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
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.ConnectException
import java.net.UnknownHostException

class LoginFragment : Fragment() {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val authViewModel: AuthViewModel by viewModel()

    private val navController by lazy { findNavController() }

    override fun onStart() {
        super.onStart()
        authViewModel.loginLastData()
    }

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
                viewError(status)
            }
            is LoadStatus.Success -> {
                navigateToMain()
            }
            is LoadStatus.InputWaiting -> {
                viewInputWaiting()
            }
        }
    }

    private fun viewInputWaiting() {
        binding.regContainer.visibility = View.VISIBLE
        binding.loadingBar.visibility = View.GONE
    }

    private fun viewError(status: LoadStatus.Error) {
        if (status.error is UnknownHostException || status.error is ConnectException) {
            Snackbar.make(binding.root, getString(R.string.connection_error), Snackbar.LENGTH_SHORT)
                .show()
        } else {
            binding.loadingBar.visibility = View.GONE
            binding.passwordInputLayout.isPasswordVisibilityToggleEnabled = false
            binding.passwordField.error = getString(R.string.login_error)
            binding.emailField.error = getString(R.string.login_error)
        }
    }

    private fun navigateToMain() {
        navController.navigate(R.id.action_loginFragment_to_mainFragment)
    }

    private fun initButtons() {
        binding.loginButton.setOnClickListener {
            login()
        }

        binding.regTextButton.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun login() {
        authViewModel.login(
            binding.emailField.text.toString(),
            binding.passwordField.text?.toList() ?: arrayListOf()
        )
    }
}
