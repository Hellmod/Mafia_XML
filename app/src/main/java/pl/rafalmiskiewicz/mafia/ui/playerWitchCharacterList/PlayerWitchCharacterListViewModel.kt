package pl.rafalmiskiewicz.mafia.ui.playerWitchCharacterList

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import pl.rafalmiskiewicz.mafia.util.db.User
import javax.inject.Inject

@HiltViewModel
class PlayerWitchCharacterListViewModel @Inject constructor() : BaseViewModel<PlayerWitchCharacterListEvent>() {

    val playerList = MutableLiveData<List<User>>()

    fun onNextClicked() {
        sendEvent(PlayerWitchCharacterListEvent.NavigateToGame)
    }
}