package pl.rafalmiskiewicz.mafia.util.db.character

abstract class CharacterInt() {

    abstract val name: String
    abstract var wakeInNight: Boolean
    abstract var prority: Float

    abstract fun makeSpecialAction(idSelectedUsers: List<Int>):Boolean
}