package pl.rafalmiskiewicz.mafia.ui.night

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.rafalmiskiewicz.mafia.databinding.ItemPlayerDetailsCheckBinding
import pl.rafalmiskiewicz.mafia.ui.base.ProductCommonClick
import pl.rafalmiskiewicz.mafia.util.db.UserWitchCheckBox
import pl.rafalmiskiewicz.mafia.util.db.character.CharacterInt

class NightAdapter(
    val characterMap: HashMap<Int, CharacterInt>,
    val checkPlayerListener: CheckPlayerListener
) : RecyclerView.Adapter<NightAdapter.ViewHolder>() {

    private var items: List<UserWitchCheckBox> = emptyList()

    // ViewHolder class
    inner class ViewHolder(val binding: ItemPlayerDetailsCheckBinding) : RecyclerView.ViewHolder(binding.root)

    // onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlayerDetailsCheckBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    // onBindViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Log.i("RMRM", "RMRM "+"onBindViewHolder() called with: item = $item")
        holder.binding.item = item
        holder.binding.listener = checkPlayerListener
        holder.binding.executePendingBindings()

        holder.binding.apply {

            playerCharacterDetails.playerCharacter.player.playerName.text = item.user.name
            playerCharacterDetails.playerCharacter.player.playerId.text = item.user.id.toString()
            playerCharacterDetails.playerCharacter.characterName.text = characterMap.get(item.user.character)?.name
            playerCharacterDetails.characterAlive.text = if (item.user.isPlayerDead) "Dead" else "Alive"

        }
    }

    // getItemCount
    override fun getItemCount(): Int {
        return items.size
    }

    // Update data
    fun updateData(newItems: List<UserWitchCheckBox>) {
        Log.i("RMRM", "RMRM "+"updateData() called with: newItems = $newItems")
        items = newItems
        notifyDataSetChanged()
    }

}

interface CheckPlayerListener {

    fun onItemClick(position: Int, isSelected: Boolean)
}