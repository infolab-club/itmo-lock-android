package club.infolab.itmo_lock.presentation.ui.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.databinding.FragmentRegistrationBinding
import club.infolab.itmo_lock.presentation.ui.auth.AuthViewModel
import club.infolab.itmo_lock.presentation.ui.auth.LoadStatus
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.ConnectException
import java.net.UnknownHostException

class RegistrationFragment : Fragment() {

    private val binding by viewBinding(FragmentRegistrationBinding::bind)

    private val registrationViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        observeStatus()
    }

    private fun observeStatus() {
        registrationViewModel.loginStatus.observe(viewLifecycleOwner) { status ->
            viewAccordingToStatus(status)
        }
    }

    private fun viewAccordingToStatus(status: LoadStatus?) {
        when (status) {
            is LoadStatus.Loading -> {
                binding.loadingBar.visibility = View.VISIBLE
            }
            is LoadStatus.Error -> {
                viewError(status.error)
            }
            is LoadStatus.Success -> {
                findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
            }
        }
    }

    private fun viewError(error: Throwable) {
        when (error) {
            is UnknownHostException -> viewConnectionError()
            is ConnectException -> viewConnectionError()
            else -> {
                viewInputError()
            }
        }
    }

    private fun viewInputError() {
        with(binding) {
            loadingBar.visibility = View.GONE
            emailField.error = getString(R.string.email_reg_error)
            nameField.error = getString(R.string.name_surname_reg_error)
            surnameField.error = getString(R.string.name_surname_reg_error)
        }
    }

    private fun viewConnectionError() {
        Snackbar.make(binding.root, getString(R.string.connection_error), Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun initButtons() {
        binding.loginTextButton.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
        binding.regButton.setOnClickListener { register() }
    }

    private fun register() {
        registrationViewModel.register(
            binding.emailField.text.toString(),
            binding.nameField.text.toString(),
            binding.surnameField.text.toString(),
            binding.passwordField.text?.toList() ?: arrayListOf()
        )
    }
}
