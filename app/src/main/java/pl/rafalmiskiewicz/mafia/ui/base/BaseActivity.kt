package pl.rafalmiskiewicz.mafia.ui.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun setOnBackClickNavigation(listener: (() -> Unit)? = null)

}