package pl.rafalmiskiewicz.mafia.ui.characterList

import android.view.LayoutInflater
import android.view.ViewGroup
import pl.rafalmiskiewicz.mafia.databinding.ItemCharacterBinding
import pl.rafalmiskiewicz.mafia.ui.base.BaseAdapter
import pl.rafalmiskiewicz.mafia.ui.base.BaseHolder
import pl.rafalmiskiewicz.mafia.ui.base.OnRecyclerListener
import pl.rafalmiskiewicz.mafia.util.db.CharacterPlayer

class CharacterListAdapter : BaseAdapter<CharacterPlayer>() {

    private var onCharacterPlayerClickListener: OnRecyclerListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<CharacterPlayer> {
        return DoctorsListHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseHolder<CharacterPlayer>, position: Int) {
        (holder as DoctorsListHolder).bind(
            items[position],
            onRecyclerListener,
            onCharacterPlayerClickListener
        )
    }

    fun setonCharacterPlayerClickListener(listener: OnRecyclerListener?) {
        this.onCharacterPlayerClickListener = listener
    }

    class DoctorsListHolder(private val itemBinding: ItemCharacterBinding) :
        BaseHolder<CharacterPlayer>(itemBinding.root), CharacterListBinder {

        override fun bind(item: CharacterPlayer, listener: OnRecyclerListener?) = Unit

        override fun bind(
            item: CharacterPlayer,
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
            item: CharacterPlayer, listener: OnRecyclerListener?,
            profileListener: OnRecyclerListener?
        )
    }
}