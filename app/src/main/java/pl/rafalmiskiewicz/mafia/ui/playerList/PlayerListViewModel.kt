package pl.rafalmiskiewicz.mafia.ui.playerList

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import pl.rafalmiskiewicz.mafia.ui.playerList.PlayerListEvent
import javax.inject.Inject

@HiltViewModel
class PlayerListViewModel@Inject constructor(): BaseViewModel<PlayerListEvent>() {

    fun onSendClick() {

    }
}