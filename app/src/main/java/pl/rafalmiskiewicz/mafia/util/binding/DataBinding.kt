package pl.rafalmiskiewicz.mafia.util.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.rafalmiskiewicz.mafia.ui.base.BaseAdapter
import pl.rafalmiskiewicz.mafia.ui.base.OnRecyclerListener
import pl.rafalmiskiewicz.mafia.ui.playerList.PlayerListAdapter

@BindingAdapter("data")
fun <T> setRecyclerData(recyclerView: RecyclerView, items: List<T>?) {
    if (recyclerView.adapter is BaseAdapter<*>) {
        items?.let {
            (recyclerView.adapter as BaseAdapter<T>).setData(it)
        }
    }
}

@BindingAdapter("onProfileClicked")
fun setOnPlayerClickListeners(
    recyclerView: RecyclerView,
    onPlayerClicked: OnRecyclerListener?
) {
    if (recyclerView.adapter is PlayerListAdapter) {
        (recyclerView.adapter as PlayerListAdapter).setonPlayerClickListener(
            onPlayerClicked
        )
    }
}