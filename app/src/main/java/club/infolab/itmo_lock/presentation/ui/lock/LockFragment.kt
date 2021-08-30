package club.infolab.itmo_lock.presentation.ui.lock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.data.entity.Room
import club.infolab.itmo_lock.databinding.FragmentLockBinding
import club.infolab.itmo_lock.domain.LockStatus
import club.infolab.itmo_lock.presentation.ui.rooms.RoomsFragment.Companion.ROOM_ARG
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.android.viewmodel.ext.android.viewModel

class LockFragment : Fragment() {

    private val lockViewModel: LockViewModel by viewModel()

    private val binding by viewBinding(FragmentLockBinding::bind)

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val room: Room = Json.decodeFromString(requireArguments().getString(ROOM_ARG)!!)
        lockViewModel.setRoomData(room)
        return inflater.inflate(R.layout.fragment_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            val room = lockViewModel.room!!

            toolbar.apply {
                menu.findItem(R.id.itemMembers).setOnMenuItemClickListener {
                    navController.popBackStack()
                    true
                }

                title = "Аудитория ${room.number}"
            }

            Glide
                .with(root)
                .load(room.picture)
                .into(roomPicture)

            roomDescription.text = room.description

            buttonOpen.setOnClickListener {
                lockViewModel.unlock()
            }
        }
        initStatusObserve()
    }

    private fun initStatusObserve() {
        lockViewModel.lockedStatus.observe(viewLifecycleOwner) { status ->
            when (status!!) {
                LockStatus.LOCKED -> viewLocked()
                LockStatus.WAITING -> viewWaiting()
                LockStatus.UNLOCKED -> viewUnlocked()
                LockStatus.ERROR -> viewError()
            }
        }
    }

    private fun viewError() {
        binding.progressCircular.apply {
            visibility = View.INVISIBLE
            isIndeterminate = false
            progress = 100
            visibility = View.VISIBLE
        }
        Snackbar.make(binding.root, getString(R.string.error_message), Snackbar.LENGTH_SHORT).show()
    }

    private fun viewUnlocked() {
        Snackbar.make(binding.root, getString(R.string.unlocked), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.dark_view))
            .setTextColor(resources.getColor(R.color.white)).show()
        binding.progressCircular.apply {
            visibility = View.INVISIBLE
            isIndeterminate = false
            progress = 100
            visibility = View.VISIBLE
        }
    }

    private fun viewLocked() {
        Snackbar.make(binding.root, getString(R.string.locked), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.dark_view))
            .setTextColor(resources.getColor(R.color.white)).show()
        binding.progressCircular.apply {
            visibility = View.INVISIBLE
            isIndeterminate = false
            progress = 100
            visibility = View.VISIBLE
        }
    }

    private fun viewWaiting() {
        binding.progressCircular.apply {
            visibility = View.INVISIBLE
            isIndeterminate = true
            visibility = View.VISIBLE
        }
    }
}
