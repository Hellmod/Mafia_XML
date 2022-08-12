package pl.rafalmiskiewicz.mafia.ui.characterList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.rafalmiskiewicz.mafia.databinding.FragmentCharacterBinding
import pl.rafalmiskiewicz.mafia.extensions.observeEvent
import pl.rafalmiskiewicz.mafia.ui.base.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class CharacterListFragment @Inject constructor() : BaseFragment() {

    private val mViewModel: CharacterListViewModel by viewModels()

    private lateinit var binding: FragmentCharacterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentCharacterBinding.inflate(layoutInflater, container, false).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = mViewModel
                characterListRecycle.adapter = CharacterListAdapter()
            }

        initObservers()

        context?.let { mViewModel.initDao(it) }
        mViewModel.readAllData.observe(
            viewLifecycleOwner
        ) { user ->
            mViewModel.playerList.value = user
            mViewModel._playersAmount.value = user.size.toString()
        }

        return binding.root
    }

    private fun initObservers() {
        mViewModel.apply {
            observeEvent(event, ::handleEvent)
        }
    }

    private fun handleEvent(event: CharacterListEvent) {
        when (event) {

        }
    }
}