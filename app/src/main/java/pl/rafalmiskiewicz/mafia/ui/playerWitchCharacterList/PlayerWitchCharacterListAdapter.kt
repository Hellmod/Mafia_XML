package pl.rafalmiskiewicz.mafia.ui.playerWitchCharacterList

import android.view.LayoutInflater
import android.view.ViewGroup
import pl.rafalmiskiewicz.mafia.databinding.ItemPlayerCharacterBinding
import pl.rafalmiskiewicz.mafia.ui.base.BaseAdapter
import pl.rafalmiskiewicz.mafia.ui.base.BaseHolder
import pl.rafalmiskiewicz.mafia.ui.base.OnRecyclerListener
import pl.rafalmiskiewicz.mafia.ui.base.ProductCommonClick
import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.db.character.CharacterInt

class PlayerWitchCharacterListAdapter(val characterMap: HashMap<Int, CharacterInt>) : BaseAdapter<User>() {

    private var onPlayerClickListener: OnRecyclerListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<User> {
        return DoctorsListHolder(
            ItemPlayerCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), characterMap
        )
    }

    override fun onBindViewHolder(holder: BaseHolder<User>, position: Int) {
        (holder as DoctorsListHolder).bind(
            items[position],
            onRecyclerListener,
            onPlayerClickListener
        )
    }

    fun setonPlayerClickListener(listener: OnRecyclerListener?) {
        this.onPlayerClickListener = listener
    }

    class DoctorsListHolder(private val itemBinding: ItemPlayerCharacterBinding, val characterMap: HashMap<Int, CharacterInt>) :
        BaseHolder<User>(itemBinding.root), PlayerListBinder {

        override fun bind(item: User, listener: OnRecyclerListener?) = Unit

        override fun bind(
            item: User,
            listener: OnRecyclerListener?,
            playerListener: OnRecyclerListener?
        ) {
            itemBinding.apply {
                player.playerName.text = item.name
                player.playerName.setOnClickListener { playerListener?.onClick(ProductCommonClick.ItemClick, item.id) }
                player.playerId.text = item.id.toString()
                characterName.text = characterMap.get(item.character)?.name
            }
        }
    }

    interface PlayerListBinder {

        fun bind(
            item: User, listener: OnRecyclerListener?,
            profileListener: OnRecyclerListener?
        )
    }
}