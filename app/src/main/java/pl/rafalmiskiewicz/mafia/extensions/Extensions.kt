package pl.rafalmiskiewicz.mafia.extensions

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import pl.rafalmiskiewicz.mafia.util.event.Event

inline fun <T : Any, L : LiveData<Event<T>>> LifecycleOwner.observeEvent(
    liveData: L,
    crossinline body: (T) -> Unit
) {
    liveData.observe(this) { it.getContentIfNotHandled()?.let(body) }
}

fun Context.toast(message: CharSequence): Toast = Toast
    .makeText(this, message, Toast.LENGTH_SHORT)
    .apply {
        show()
    }

fun Context.toast(message: Int): Toast = Toast
    .makeText(this, message, Toast.LENGTH_SHORT)
    .apply {
        show()
    }

