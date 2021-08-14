package club.infolab.itmo_lock.presentation.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    private lateinit var binding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding
            .bind(inflater.inflate(R.layout.login_fragment, container, false))
        initButtons()
        return binding.root
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

    }
}