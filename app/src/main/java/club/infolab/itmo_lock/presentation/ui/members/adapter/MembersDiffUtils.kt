package club.infolab.itmo_lock.presentation.ui.members.adapter

import androidx.recyclerview.widget.DiffUtil
import club.infolab.itmo_lock.presentation.ui.rooms.adapter.Member

class MembersDiffUtils : DiffUtil.ItemCallback<Member>() {

    override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean =
        oldItem.idUser == newItem.idUser

    override fun areContentsTheSame(
        oldItem: Member,
        newItem: Member
    ): Boolean = oldItem == newItem
}
