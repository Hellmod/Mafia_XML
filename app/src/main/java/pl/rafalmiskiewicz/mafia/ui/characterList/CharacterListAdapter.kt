package pl.rafalmiskiewicz.mafia.ui.characterList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.rafalmiskiewicz.mafia.databinding.ItemCharacterBinding
import pl.rafalmiskiewicz.mafia.ui.base.BaseAdapter
import pl.rafalmiskiewicz.mafia.ui.base.BaseHolder
import pl.rafalmiskiewicz.mafia.ui.base.OnRecyclerListener
import pl.rafalmiskiewicz.mafia.ui.base.ProductCommonClick
import pl.rafalmiskiewicz.mafia.util.db.CharacterPlayer

class CharacterListAdapter : BaseAdapter<CharacterPlayer>(),AutoUpdatableAdapter {

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
                characterName.setOnClickListener { CharacterListener?.onClick(ProductCommonClick.ItemClick,item.id) }
                characterId.text = item.id.toString()
            }
            displayCounter(
                itemBinding,
                item,
                listener
            )
        }

        private fun displayCounter(
            binding: ItemCharacterBinding,
            item: CharacterPlayer,
            clickListener: OnRecyclerListener?
        ) {
            binding.apply {
                val isCounterActive = item.count > 0
                if (item.count == 1) {
                    counterLayout.activateCounter(true)
                }
                counterLayout.setPlusListener {
                    clickListener?.onClick(
                        ProductCommonClick.PlusAmount,
                        item.id
                    )
                }

                if (isCounterActive) {
                    counterLayout.apply {
                        setCount(item.count)
                        setEnabledPlus(0 < item.amount)
                        setMinusListener {
                            clickListener?.onClick(
                                ProductCommonClick.MinusAmount,
                                item.id
                            )
                        }
                    }
                } else {
                    counterLayout.activateCounter(false)
                }
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

interface AutoUpdatableAdapter {

    fun <T> RecyclerView.Adapter<*>.autoNotify(
        oldList: List<T>,
        newList: List<T>,
        compare: (T, T) -> Boolean
    ) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return compare(oldList[oldItemPosition], newList[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size
        })

        diff.dispatchUpdatesTo(this)
    }
}