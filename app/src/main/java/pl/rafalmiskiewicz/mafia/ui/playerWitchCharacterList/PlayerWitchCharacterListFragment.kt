package pl.rafalmiskiewicz.mafia.ui.playerWitchCharacterList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.rafalmiskiewicz.mafia.databinding.FragmentPlayerCharacterBinding
import pl.rafalmiskiewicz.mafia.extensions.observeEvent
import pl.rafalmiskiewicz.mafia.ui.base.BaseFragment
import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.db.UserDatabase
import pl.rafalmiskiewicz.mafia.util.db.UserRepository
import javax.inject.Inject

@AndroidEntryPoint
class PlayerWitchCharacterListFragment @Inject constructor() : BaseFragment() {

    private val mViewModel: PlayerWitchCharacterListViewModel by viewModels()

    private lateinit var binding: FragmentPlayerCharacterBinding

    lateinit var readAllData: LiveData<List<User>>
    private lateinit var repository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentPlayerCharacterBinding.inflate(layoutInflater, container, false).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = mViewModel
                playerCharacterListRecycle.adapter = PlayerWitchCharacterListAdapter(mViewModel.characterMap)
            }

        initObservers()
        context?.let { initDao(it) }
        readAllData.observe(
            viewLifecycleOwner
        ) { user ->
            mViewModel.playerList.value = user
        }
        return binding.root
    }

    fun initDao(context: Context) {
        val userDao = UserDatabase.getDatabase(context).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    private fun initObservers() {
        mViewModel.apply {
            observeEvent(event, ::handleEvent)
        }
    }

    private fun handleEvent(event: PlayerWitchCharacterListEvent) {
        when (event) {
            PlayerWitchCharacterListEvent.NavigateToGame -> {
                val action = PlayerWitchCharacterListFragmentDirections.actionPlayerWitchCharacterListFragmentToNightFragment()
                findNavController().navigate(action)
            }
        }
    }
}