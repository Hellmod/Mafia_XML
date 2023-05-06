package pl.rafalmiskiewicz.mafia.util.db.character

import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.rafalmiskiewicz.mafia.util.db.UserDao

class Pirates(
    private val initDatabase: UserDao
) : CharacterInt() {

    override val name = "Pirat"
    override var wakeInNight: Boolean = true
    override var prority: Float = 1f

    override fun makeSpecialAction(idSelectedUsers: List<Int>): Boolean {
        Log.i("RMRM", "RMRM " + "$name makeSpecialAction() called with: idSelectedUsers = $idSelectedUsers")
        if (idSelectedUsers.size > 1) return false
        if (idSelectedUsers.isEmpty()) return false
        killPlayer(idSelectedUsers[0])
        return true
    }

    private fun killPlayer(userId: Int) {
        GlobalScope.launch {
            initDatabase.updateIsPlayerDead(userId, true)
        }
    }

}