package pl.rafalmiskiewicz.mafia.ui.playerList

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import pl.rafalmiskiewicz.mafia.ui.base.ClickType
import pl.rafalmiskiewicz.mafia.util.db.User
import javax.inject.Inject

@HiltViewModel
class PlayerListViewModel @Inject constructor() : BaseViewModel<PlayerListEvent>() {

    val playerList = MutableLiveData<List<User>>()
    val playerName = MutableLiveData<String>()

    fun onProfileClicked(type: ClickType, id: Int) {
        sendEvent(PlayerListEvent.OnProfileClicked(id))
    }

    fun onAddClicked() {
        sendEvent(
            PlayerListEvent.OnAddClicked(
                User(
                    id = 0,
                    name = playerName.value ?: "ERROR",
                    character = null
                )
            )
        )
    }

    fun onNextClicked() {
        sendEvent(PlayerListEvent.NavigateToCharacter)
    }
}