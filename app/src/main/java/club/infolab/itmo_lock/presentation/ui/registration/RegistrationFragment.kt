package club.infolab.itmo_lock.presentation.ui.registration

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.databinding.RegistrationFragmentBinding

class RegistrationFragment : Fragment() {
    private lateinit var binding: RegistrationFragmentBinding

    private lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegistrationFragmentBinding
            .bind(inflater.inflate(R.layout.registration_fragment, container, false))
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        binding.loginTextButton.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
        
        binding.regButton.setOnClickListener { register() }
    }

    private fun register() {
        
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}