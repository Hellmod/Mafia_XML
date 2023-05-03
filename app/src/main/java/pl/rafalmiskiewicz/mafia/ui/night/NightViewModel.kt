package pl.rafalmiskiewicz.mafia.ui.night

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import pl.rafalmiskiewicz.mafia.ui.base.ClickType
import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.db.UserDao
import pl.rafalmiskiewicz.mafia.util.db.UserWitchCheckBox
import pl.rafalmiskiewicz.mafia.util.db.character.CharacterInt
import javax.inject.Inject

@HiltViewModel
class NightViewModel @Inject constructor(
    val initDatabase: UserDao,
    val characterMap: HashMap<Int, CharacterInt>
) : BaseViewModel<NightEvent>() {

    var charactersListInPlay = listOf<Int>()
    val characterPointerTurn = 0
    val isNight = true
    val playerList = MutableLiveData<List<UserWitchCheckBox>>()

    fun onProfileClicked(type: ClickType, id: Int) {
        sendEvent(NightEvent.KillPlayer(id))
    }

    fun onNextClicked() {
        sendEvent(NightEvent.OnNextClick)
    }

    fun onTestsClicked() {
        Log.i("RMRM", "RMRM "+"onTestsClicked() called charactersListInPlay: $charactersListInPlay")
        sendEvent(NightEvent.OnTestsClick)
    }
}