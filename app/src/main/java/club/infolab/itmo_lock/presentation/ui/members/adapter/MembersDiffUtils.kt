package club.infolab.itmo_lock.presentation.ui.rooms.adapter

import androidx.recyclerview.widget.DiffUtil

class MembersDiffUtils : DiffUtil.ItemCallback<Member>() {

    override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(
        oldItem: Member,
        newItem: Member
    ): Boolean = oldItem == newItem
}
