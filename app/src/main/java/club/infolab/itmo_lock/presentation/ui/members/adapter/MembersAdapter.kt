package club.infolab.itmo_lock.presentation.ui.members.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.databinding.ItemMemberBinding
import club.infolab.itmo_lock.presentation.ui.rooms.adapter.Member

class MembersAdapter(private val onActivateCallback: (member: Member) -> Unit) :
    RecyclerView.Adapter<MembersAdapter.MembersViewHolder>() {

    private var members = AsyncListDiffer(this, MembersDiffUtils())

    fun setData(data: List<Member>) {
        members.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        return MembersViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_member,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        with(members.currentList[position]) {
            holder.binding.roomNumber.text = "$surname $name"
            holder.binding.isThereAccess.isActivated = isThereAccess
        }

        holder.binding.root.setOnClickListener {
            onActivateCallback(members.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return members.currentList.size
    }

    class MembersViewHolder(val binding: ItemMemberBinding) : RecyclerView.ViewHolder(binding.root)
}
