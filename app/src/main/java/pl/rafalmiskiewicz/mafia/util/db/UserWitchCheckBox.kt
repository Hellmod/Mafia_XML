package pl.rafalmiskiewicz.mafia.util.db

data class UserWitchCheckBox(
    var isSelected: Boolean = false,
    val user: User
)