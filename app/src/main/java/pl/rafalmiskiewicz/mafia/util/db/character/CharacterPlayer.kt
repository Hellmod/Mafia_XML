package pl.rafalmiskiewicz.mafia.util.db.character

data class CharacterPlayer(
    val id: Int,
    val name: String,
    var count: Int,
    val amount: Int,
)