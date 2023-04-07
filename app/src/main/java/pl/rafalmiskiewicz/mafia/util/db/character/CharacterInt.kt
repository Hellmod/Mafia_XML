package pl.rafalmiskiewicz.mafia.util.db.character

abstract class CharacterInt() {
    abstract val name:String

    abstract fun makeSpecificAction(playerId: Int)
}