package club.infolab.itmo_lock.presentation.ui.lock

import android.graphics.drawable.Drawable
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
import club.infolab.itmo_lock.presentation.ui.rooms.RoomsFragment
import club.infolab.itmo_lock.presentation.ui.rooms.RoomsFragment.Companion.ROOM_ARG
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.ttlock.bl.sdk.api.TTLockClient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
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
        viewWaiting()
        initView()
    }

    private val requestListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            viewError()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            viewLocked()
            return false
        }
    }

    private fun initView() {
        with(binding) {
            val room = lockViewModel.room!!

            toolbar.apply {
                with(menu.findItem(R.id.itemMembers)) {
                    isVisible = false
                    setOnMenuItemClickListener {
                        val bundle = Bundle()
                        bundle.putString(RoomsFragment.ROOM_ARG, Json.encodeToString(room))
                        navController.navigate(R.id.action_lockFragment_to_membersFragment, bundle)
                        false
                    }
                }

                title = "Аудитория ${room.number}"
            }

            Glide
                .with(root)
                .load(room.picture)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(requestListener)
                .into(roomPicture)

            roomDescription.text = room.description

            buttonUnlock.setOnClickListener {
                ensureBluetoothIsEnabled()
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

        lockViewModel.userInfo.observe(viewLifecycleOwner) { user ->
            with(binding.toolbar.menu.findItem(R.id.itemMembers)) {
                isVisible = user.isAdmin
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

    private fun ensureBluetoothIsEnabled() {
        if (!TTLockClient.getDefault().isBLEEnabled(requireContext())) {
            TTLockClient.getDefault().requestBleEnable(requireActivity())
        }
    }
}
