package pl.rafalmiskiewicz.mafia.ui.characterList

import pl.rafalmiskiewicz.mafia.ui.playerList.PlayerListEvent
import pl.rafalmiskiewicz.mafia.util.event.BaseEvent

sealed class CharacterListEvent : BaseEvent{
    object OnNextClick : CharacterListEvent()
}