package pl.rafalmiskiewicz.mafia.ui.playerList

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.rafalmiskiewicz.mafia.data.common.PlayerItem
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerListViewModel @Inject constructor() : BaseViewModel<PlayerListEvent>() {

    val playerList = MutableLiveData<List<PlayerItem>>()
    val playerName = MutableLiveData<String>()

    fun onProfileClicked(id: Int) {
        playerList.value = playerList.value?.filter { it.id != id }
    }

    fun onAddClicked() {

        val newPlayerList = playerList.value?.toMutableList()
        newPlayerList?.add(
            PlayerItem(
                id = newPlayerList.maxOf { it.id } + 1,
                name = playerName.value ?: "ERROR"
            )
        )
        playerList.value = newPlayerList ?: emptyList()
    }

    fun onNextClicked() {
        sendEvent(PlayerListEvent.NavigateToCharacter)
    }
}