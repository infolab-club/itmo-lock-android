package club.infolab.itmo_lock.ui.members

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import club.infolab.itmo_lock.R

class MembersFragment : Fragment() {

    companion object {
        fun newInstance() = MembersFragment()
    }

    private lateinit var viewModel: MembersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.members_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MembersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}