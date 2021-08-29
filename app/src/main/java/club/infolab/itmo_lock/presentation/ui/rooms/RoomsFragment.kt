package club.infolab.itmo_lock.presentation.ui.rooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.databinding.FragmentMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RoomsFragment : Fragment() {
    private val roomsViewModel: RoomsViewModel by viewModel()
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val roomsRecycler = binding.roomsRecycler
        val roomsAdapter = RoomsAdapter()
        roomsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = roomsAdapter
        }
        roomsViewModel.rooms.observe(viewLifecycleOwner) {
            roomsAdapter.setData(it)
        }
    }
}