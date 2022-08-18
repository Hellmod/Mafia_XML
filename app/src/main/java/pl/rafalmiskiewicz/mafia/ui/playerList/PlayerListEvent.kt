package pl.rafalmiskiewicz.mafia.ui.playerList

import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.event.BaseEvent

sealed class PlayerListEvent : BaseEvent {
    object NavigateToCharacter : PlayerListEvent()
    class OnProfileClicked(val id: Int) : PlayerListEvent()
    class OnAddClicked(val user: User) : PlayerListEvent()
}