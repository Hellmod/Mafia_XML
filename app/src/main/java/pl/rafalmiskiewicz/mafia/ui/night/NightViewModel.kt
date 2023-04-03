package pl.rafalmiskiewicz.mafia.ui.night

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import pl.rafalmiskiewicz.mafia.util.db.UserDao
import pl.rafalmiskiewicz.mafia.util.db.character.CharacterPlayer
import javax.inject.Inject

@HiltViewModel
class NightViewModel @Inject constructor(
    val initDatabase: UserDao
) : BaseViewModel<NightEvent>() {

    val characterPlayerList = MutableLiveData<List<CharacterPlayer>>()

    fun onNextClicked() {
        Log.i("RMRM", "RMRM " + "onNextClicked() called")
        sendEvent(NightEvent.OnNextClick)
    }

    fun onTetsClicked() {
        Log.i("RMRM", "RMRM "+"onTetsClicked() called playerList = ${characterPlayerList.value}")
    }
}