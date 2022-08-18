package pl.rafalmiskiewicz.mafia.ui.playerList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pl.rafalmiskiewicz.mafia.databinding.FragmentStartBinding
import pl.rafalmiskiewicz.mafia.extensions.observeEvent
import pl.rafalmiskiewicz.mafia.ui.base.BaseFragment
import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.db.UserDatabase
import pl.rafalmiskiewicz.mafia.util.db.UserRepository
import javax.inject.Inject

@AndroidEntryPoint
class PlayerListFragment @Inject constructor() : BaseFragment() {

    private val mViewModel: PlayerListViewModel by viewModels()

    private lateinit var binding: FragmentStartBinding

    lateinit var readAllData: LiveData<List<User>>
    private lateinit var repository: UserRepository

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

    private fun handleEvent(event: PlayerListEvent) {
        when (event) {
            PlayerListEvent.NavigateToCharacter -> {
                val action = PlayerListFragmentDirections.actionStartFragmentToCharacterFragment()
                findNavController().navigate(action)
            }
            is PlayerListEvent.OnAddClicked -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    repository.addUser(event.user)
                }

            }
            is PlayerListEvent.OnProfileClicked -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    repository.deleteUser(event.id)
                }
            }
        }
    }
}