package club.infolab.itmo_lock.presentation.ui.rooms

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.data.entity.KeyObj
import club.infolab.itmo_lock.databinding.FragmentMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RoomsFragment : Fragment() {
    private val roomsViewModel: RoomsViewModel by viewModel()
    private val binding by viewBinding(FragmentMainBinding::bind)
    private lateinit var roomsRecycler: RecyclerView
    private lateinit var roomsAdapter: RoomsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        Log.i(TAG, "onViewCreated: ${KeyObj.token}")
    }

    private fun initView() {
        roomsRecycler = binding.roomsRecycler
        roomsRecycler.layoutManager = LinearLayoutManager(requireContext())
        roomsAdapter = RoomsAdapter()
        roomsRecycler.adapter = roomsAdapter
        roomsViewModel.updateData()
    }

    private fun initViewModel() {
        roomsViewModel.rooms.observe(viewLifecycleOwner) {
            if (roomsViewModel.viewingRooms != null) {
                val diffUtilCallback = RoomsDiffUtilCallback(roomsViewModel.viewingRooms!!, it)
                val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
                roomsAdapter.rooms = it
                diffUtilResult.dispatchUpdatesTo(roomsAdapter)

            } else {
                roomsAdapter.rooms = it
                roomsViewModel.viewingRooms = it
            }
        }
    }

}