package club.infolab.itmo_lock.presentation.ui.members

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.databinding.FragmentMembersBinding
import club.infolab.itmo_lock.presentation.ui.members.adapter.MembersAdapter
import club.infolab.itmo_lock.presentation.ui.rooms.RoomsFragment.Companion.ROOM_ARG
import club.infolab.itmo_lock.presentation.ui.rooms.adapter.MembersDecoration
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.android.viewmodel.ext.android.viewModel

class MembersFragment : Fragment() {

    private val membersViewModel: MembersViewModel by viewModel()

    private val binding by viewBinding(FragmentMembersBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_members, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        membersViewModel.room = Json.decodeFromString(requireArguments().getString(ROOM_ARG)!!)
        initRecycler()
    }

    private fun initRecycler() {
        val membersAdapter = MembersAdapter {
            membersViewModel.changeUserAccess(it)
        }
        binding.membersRecycler.apply {
            adapter = membersAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MembersDecoration())
        }

        membersViewModel.members.observe(viewLifecycleOwner) {
            membersAdapter.setData(it)
        }
    }
}
