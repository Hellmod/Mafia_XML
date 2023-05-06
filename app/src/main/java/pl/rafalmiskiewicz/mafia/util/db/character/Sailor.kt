package pl.rafalmiskiewicz.mafia.util.db.character

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.rafalmiskiewicz.mafia.util.db.UserDao

class Sailor(
    private val initDatabase: UserDao
) : CharacterInt() {

    override val name = "Marynarz"
    override var wakeInNight: Boolean = false
    override var prority: Float = 1f

    override fun makeSpecialAction(idSelectedUsers: List<Int>) {
        Log.i("RMRM", "RMRM "+"makeSpecialAction() called with: idSelectedUsers = $idSelectedUsers")
    }

    private fun killPlayer(userId: Int) {
        GlobalScope.launch {
            initDatabase.updateIsPlayerDead(userId, true)
        }
    }

}