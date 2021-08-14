package club.infolab.itmo_lock.presentation.ui.rooms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.databinding.MainFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RoomsFragment : Fragment() {
    private val roomsViewModel: RoomsViewModel by viewModel()
    private lateinit var binding: MainFragmentBinding
    private lateinit var roomsRecycler: RecyclerView
    private lateinit var roomsAdapter: RoomsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding
            .bind(inflater.inflate(R.layout.main_fragment, container, false))
        initView()
        initViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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