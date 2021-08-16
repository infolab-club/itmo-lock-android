package club.infolab.itmo_lock.presentation.ui.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.databinding.RegistrationFragmentBinding
import club.infolab.itmo_lock.presentation.ui.auth.LoginStatus
import org.koin.android.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {
    private lateinit var binding: RegistrationFragmentBinding

    private val registrationViewModel: RegistrationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegistrationFragmentBinding
            .bind(inflater.inflate(R.layout.registration_fragment, container, false))
        initButtons()
        initStatusObserver()
        return binding.root
    }

    private fun initStatusObserver() {
        registrationViewModel.loginStatus.observe(viewLifecycleOwner) { status ->
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
                findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
            }
        }
    }

    private fun initButtons() {
        binding.loginTextButton.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        binding.regButton.setOnClickListener { register() }
    }

    private fun register() {
        registrationViewModel.registerWithData(
            binding.emailField.text.toString(),
            binding.nameField.text.toString(),
            binding.surnameField.text.toString(),
            binding.passwordField.text?.toList() ?: arrayListOf()
        )
    }

}