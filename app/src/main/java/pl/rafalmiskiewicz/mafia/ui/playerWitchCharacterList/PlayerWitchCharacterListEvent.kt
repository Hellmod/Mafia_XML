package pl.rafalmiskiewicz.mafia.ui.playerWitchCharacterList

import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.event.BaseEvent

sealed class PlayerWitchCharacterListEvent : BaseEvent {
    object NavigateToCharacter : PlayerWitchCharacterListEvent()
    class OnProfileClicked(val id: Int) : PlayerWitchCharacterListEvent()
    class OnAddClicked(val user: User) : PlayerWitchCharacterListEvent()
}