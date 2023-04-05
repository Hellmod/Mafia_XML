package pl.rafalmiskiewicz.mafia.ui.night

import pl.rafalmiskiewicz.mafia.util.event.BaseEvent

sealed class NightEvent : BaseEvent{
    object OnNextClick : NightEvent()
    object OnTestsClick : NightEvent()
    class KillPlayer(val userId: Int) : NightEvent()
}