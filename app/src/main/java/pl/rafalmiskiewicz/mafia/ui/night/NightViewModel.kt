package pl.rafalmiskiewicz.mafia.ui.night

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.db.UserDao
import javax.inject.Inject

@HiltViewModel
class NightViewModel @Inject constructor(
    val initDatabase: UserDao
) : BaseViewModel<NightEvent>() {

    val playerList = MutableLiveData<List<User>>()

    fun onNextClicked() {
        Log.i("RMRM", "RMRM " + "onNextClicked() called")
        sendEvent(NightEvent.OnNextClick)
    }

    fun onTetsClicked() {
        Log.i("RMRM", "RMRM "+"onTetsClicked() called playerList = ${playerList.value}")
    }
}