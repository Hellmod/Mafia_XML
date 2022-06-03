package pl.rafalmiskiewicz.mafia.ui.base

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected fun setOnBackClickNavigation(listener: (() -> Unit)? = null) {
        (activity as? BaseActivity)?.setOnBackClickNavigation(listener)
    }

}