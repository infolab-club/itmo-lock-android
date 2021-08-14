package club.infolab.itmo_lock.presentation.ui.lock

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.databinding.LockFragmentBinding
import club.infolab.itmo_lock.domain.usecases.lock.LockStatus
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class LockFragment : Fragment() {
    private val lockViewModel: LockViewModel by viewModel()
//    private lateinit var lockViewModel: LockViewModel
    private lateinit var binding: LockFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LockFragmentBinding
                .bind(inflater.inflate(R.layout.lock_fragment, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        lockViewModel = ViewModelProviders.of(this).get(LockViewModel::class.java)
        initView()
    }

    private fun initView() {
        binding.toolbar.menu.findItem(R.id.itemMembers).setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_lockFragment_to_membersFragment)
            true
        }

        binding.buttonOpen.setOnClickListener {
            unlock()
        }
        initStatusObserve()
    }

    private fun initStatusObserve() {
        lockViewModel.lockedStatus.observe(viewLifecycleOwner) {
            Log.d("FRAGMENT", it.toString())
            when(it) {
                LockStatus.LOCKED -> viewLocked()
                LockStatus.WAITING -> viewWaiting()
                LockStatus.UNLOCKED -> viewUnlocked()
                LockStatus.ERROR -> viewError()
            }
        }
    }

    private fun viewError() {
        binding.progressCircular.visibility = View.INVISIBLE
        binding.progressCircular.isIndeterminate = false
        binding.progressCircular.progress = 100
        binding.progressCircular.visibility = View.VISIBLE
        Snackbar.make(binding.root, getString(R.string.error_message), Snackbar.LENGTH_SHORT).show()
    }

    private fun viewUnlocked() {
        Snackbar.make(binding.root, getString(R.string.unlocked), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.dark_view))
            .setTextColor(resources.getColor(R.color.white)).show()
        binding.progressCircular.visibility = View.INVISIBLE
        binding.progressCircular.isIndeterminate = false
        binding.progressCircular.progress = 100
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun viewLocked() {
        binding.progressCircular.visibility = View.INVISIBLE
        binding.progressCircular.isIndeterminate = false
        binding.progressCircular.progress = 100
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun viewWaiting() {
        binding.progressCircular.visibility = View.INVISIBLE
        binding.progressCircular.isIndeterminate = true
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun unlock() {
        lockViewModel.unlock()
    }

}