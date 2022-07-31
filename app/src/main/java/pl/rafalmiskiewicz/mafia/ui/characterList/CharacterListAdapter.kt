package pl.rafalmiskiewicz.mafia.ui.characterList

import android.view.LayoutInflater
import android.view.ViewGroup
import pl.rafalmiskiewicz.mafia.data.common.CharacterItem
import pl.rafalmiskiewicz.mafia.databinding.ItemCharacterBinding
import pl.rafalmiskiewicz.mafia.ui.base.BaseAdapter
import pl.rafalmiskiewicz.mafia.ui.base.BaseHolder
import pl.rafalmiskiewicz.mafia.ui.base.OnRecyclerListener

class CharacterListAdapter : BaseAdapter<CharacterItem>() {

    private var onCharacterClickListener: OnRecyclerListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<CharacterItem> {
        return DoctorsListHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseHolder<CharacterItem>, position: Int) {
        (holder as DoctorsListHolder).bind(
            items[position],
            onRecyclerListener,
            onCharacterClickListener
        )
    }

    fun setonCharacterClickListener(listener: OnRecyclerListener?) {
        this.onCharacterClickListener = listener
    }

    class DoctorsListHolder(private val itemBinding: ItemCharacterBinding) :
        BaseHolder<CharacterItem>(itemBinding.root), CharacterListBinder {

        override fun bind(item: CharacterItem, listener: OnRecyclerListener?) = Unit

        override fun bind(
            item: CharacterItem,
            listener: OnRecyclerListener?,
            CharacterListener: OnRecyclerListener?
        ) {
            itemBinding.apply {
                characterName.text = item.name
                characterName.setOnClickListener { CharacterListener?.onClick(item.id) }
                characterId.text = item.id.toString()
            }
        }
    }

    interface CharacterListBinder {

        fun bind(
            item: CharacterItem, listener: OnRecyclerListener?,
            profileListener: OnRecyclerListener?
        )
    }
}