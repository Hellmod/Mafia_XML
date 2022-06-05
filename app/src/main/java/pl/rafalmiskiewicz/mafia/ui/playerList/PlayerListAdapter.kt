package pl.rafalmiskiewicz.mafia.ui.playerList

import android.view.LayoutInflater
import android.view.ViewGroup
import pl.rafalmiskiewicz.mafia.data.common.PlayerItem
import pl.rafalmiskiewicz.mafia.databinding.ItemPlayerBinding
import pl.rafalmiskiewicz.mafia.ui.base.BaseAdapter
import pl.rafalmiskiewicz.mafia.ui.base.BaseHolder
import pl.rafalmiskiewicz.mafia.ui.base.OnRecyclerListener

class PlayerListAdapter : BaseAdapter<PlayerItem>() {

    private var onPlayerClickListener: OnRecyclerListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<PlayerItem> {
        return DoctorsListHolder(
            ItemPlayerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseHolder<PlayerItem>, position: Int) {
        (holder as DoctorsListHolder).bind(
            items[position],
            onRecyclerListener,
            onPlayerClickListener
        )
    }

    fun setonPlayerClickListener(listener: OnRecyclerListener?) {
        this.onPlayerClickListener = listener
    }

    class DoctorsListHolder(private val itemBinding: ItemPlayerBinding) :
        BaseHolder<PlayerItem>(itemBinding.root), PlayerListBinder {

        override fun bind(item: PlayerItem, listener: OnRecyclerListener?) = Unit

        override fun bind(
            item: PlayerItem,
            listener: OnRecyclerListener?,
            playerListener: OnRecyclerListener?
        ) {
            itemBinding.apply {
                playerName.text = item.name
                playerName.setOnClickListener { playerListener?.onClick(item.id) }
                playerId.text = item.id.toString()
            }
        }
    }

    interface PlayerListBinder {

        fun bind(
            item: PlayerItem, listener: OnRecyclerListener?,
            profileListener: OnRecyclerListener?
        )
    }
}