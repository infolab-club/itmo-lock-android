package club.infolab.itmo_lock.presentation.ui.rooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.data.entity.Room
import club.infolab.itmo_lock.databinding.FragmentRoomsBinding
import club.infolab.itmo_lock.presentation.ui.rooms.adapter.MembersDecoration
import club.infolab.itmo_lock.presentation.ui.rooms.adapter.RoomsAdapter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.android.viewmodel.ext.android.viewModel

class RoomsFragment : Fragment() {

    private val roomsViewModel: RoomsViewModel by viewModel()

    private val binding by viewBinding(FragmentRoomsBinding::bind)

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_rooms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.setNavigationOnClickListener {
            roomsViewModel.logout {
                navController.navigate(R.id.action_mainFragment_to_loginFragment)
            }
        }
        initRecycler()
    }

    private val navigateCallback: (Room) -> Unit = { room ->
        val bundle = Bundle()
        bundle.putString(ROOM_ARG, Json.encodeToString(room))
        navController.navigate(R.id.action_mainFragment_to_lockFragment, bundle)
    }

    private fun initRecycler() {
        val roomsRecycler = binding.roomsRecycler
        val roomsAdapter = RoomsAdapter(navigateCallback)

        roomsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = roomsAdapter
            addItemDecoration(MembersDecoration())
        }

        roomsViewModel.rooms.observe(viewLifecycleOwner) {
            roomsAdapter.setData(it)
        }
    }

    companion object {
        const val ROOM_ARG = "room"
    }
}
