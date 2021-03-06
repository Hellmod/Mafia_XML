package pl.rafalmiskiewicz.mafia.ui.playerList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.rafalmiskiewicz.mafia.data.common.PlayerItem
import pl.rafalmiskiewicz.mafia.databinding.FragmentStartBinding
import pl.rafalmiskiewicz.mafia.extensions.observeEvent
import pl.rafalmiskiewicz.mafia.ui.base.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class PlayerListFragment @Inject constructor() : BaseFragment() {

    private val mViewModel: PlayerListViewModel by viewModels()

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentStartBinding.inflate(layoutInflater, container, false).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = mViewModel
                playerListRecycle.adapter = PlayerListAdapter()
            }

        initObservers()
        initList()

        return binding.root
    }

    private fun initList() {
        mViewModel.playerList.value = listOf(
            PlayerItem(
                id = 0,
                name = "Rafał"
            ),
            PlayerItem(
                id = 1,
                name = "Rafał"
            ),
            PlayerItem(
                id = 2,
                name = "Rafał"
            ),
            PlayerItem(
                id = 3,
                name = "Rafał"
            ),
        )
    }

    private fun initObservers() {
        mViewModel.apply {
            observeEvent(event, ::handleEvent)
        }
    }

    private fun handleEvent(event: PlayerListEvent) {
        when (event) {

        }
    }
}