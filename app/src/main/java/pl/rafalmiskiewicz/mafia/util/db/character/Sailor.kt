package pl.rafalmiskiewicz.mafia.util.db.character

import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.rafalmiskiewicz.mafia.util.db.UserDao

class Sailor(
    private val initDatabase: UserDao
) : CharacterInt() {

    override val name = "Marynarz"
    override var wakeInNight: Boolean = false
    override var prority: Float = 1f
    override var instruction: String = "Marynarz nie ma specjalnej akcji."

    override fun makeSpecialAction(idSelectedUsers: List<Int>): Boolean {
        Log.i("RMRM", "RMRM " + "$name makeSpecialAction() called with: idSelectedUsers = $idSelectedUsers")
        return false
    }

    private fun killPlayer(userId: Int) {
        GlobalScope.launch {
            initDatabase.updateIsPlayerDead(userId, true)
        }
    }

}