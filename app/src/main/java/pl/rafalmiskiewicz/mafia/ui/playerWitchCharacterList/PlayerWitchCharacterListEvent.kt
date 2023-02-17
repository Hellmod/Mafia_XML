package pl.rafalmiskiewicz.mafia.ui.playerWitchCharacterList

import pl.rafalmiskiewicz.mafia.util.event.BaseEvent

sealed class PlayerWitchCharacterListEvent : BaseEvent {
    object NavigateToGame : PlayerWitchCharacterListEvent()
}