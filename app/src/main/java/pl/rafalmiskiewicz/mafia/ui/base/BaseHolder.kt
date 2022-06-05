package pl.rafalmiskiewicz.mafia.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder<in T>(containerView: View) :
    RecyclerView.ViewHolder(containerView) {
    abstract fun bind(item: T, listener: OnRecyclerListener? = null)
}