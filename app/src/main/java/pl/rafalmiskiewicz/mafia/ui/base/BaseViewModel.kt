package pl.rafalmiskiewicz.mafia.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.rafalmiskiewicz.mafia.util.event.BaseEvent
import pl.rafalmiskiewicz.mafia.util.event.Event

abstract class BaseViewModel<T : BaseEvent> : ViewModel() {

    private val mEvent: MutableLiveData<Event<T>> = MutableLiveData()
    val event: LiveData<Event<T>> = mEvent

    fun sendEvent(event: T) {
        mEvent.value = Event(event)
    }
}