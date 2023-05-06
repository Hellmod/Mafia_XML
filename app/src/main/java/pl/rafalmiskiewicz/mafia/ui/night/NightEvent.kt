package pl.rafalmiskiewicz.mafia.ui.night

import pl.rafalmiskiewicz.mafia.util.event.BaseEvent

sealed class NightEvent : BaseEvent {
    class OnNextClick(val idSelectedUsers: List<Int>) : NightEvent()
    object OnTestsClick : NightEvent()
}