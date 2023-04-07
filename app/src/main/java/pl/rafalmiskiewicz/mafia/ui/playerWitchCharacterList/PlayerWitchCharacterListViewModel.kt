package pl.rafalmiskiewicz.mafia.ui.playerWitchCharacterList

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.db.character.CharacterInt
import javax.inject.Inject

@HiltViewModel
class PlayerWitchCharacterListViewModel @Inject constructor(
    val characterMap: HashMap<Int, CharacterInt>
) : BaseViewModel<PlayerWitchCharacterListEvent>() {

    val playerList = MutableLiveData<List<User>>()

    fun onNextClicked() {
        sendEvent(PlayerWitchCharacterListEvent.NavigateToGame)
    }
}