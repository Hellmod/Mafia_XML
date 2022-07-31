package pl.rafalmiskiewicz.mafia.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}


fun TextView.setColor(color: Int) {
    setTextColor(context.getColor(color))
}

fun View.setBackgroundTint(color: Int) {
    background.setTint(context.getColor(color))
}

fun EditText.setBackground(drawable: Int) {
    background = ContextCompat.getDrawable(context, drawable)
}

inline fun EditText.afterTextChanged(crossinline action: (text: String?) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            action(p0?.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    })
}