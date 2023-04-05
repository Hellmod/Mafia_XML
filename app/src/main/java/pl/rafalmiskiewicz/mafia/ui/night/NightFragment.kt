package pl.rafalmiskiewicz.mafia.ui.night

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pl.rafalmiskiewicz.mafia.databinding.FragmentNightBinding
import pl.rafalmiskiewicz.mafia.extensions.observeEvent
import pl.rafalmiskiewicz.mafia.ui.base.BaseFragment
import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.db.UserRepository
import pl.rafalmiskiewicz.mafia.util.db.character.CharacterPlayer
import pl.rafalmiskiewicz.mafia.util.db.character.Pirates
import javax.inject.Inject

@AndroidEntryPoint
class NightFragment @Inject constructor() : BaseFragment() {

    private val mViewModel: NightViewModel by viewModels()

    private lateinit var binding: FragmentNightBinding

    lateinit var readAllData: LiveData<List<User>>
    private lateinit var repository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentNightBinding.inflate(layoutInflater, container, false).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = mViewModel
                playerCharacterListRecycle.adapter = NightAdapter()
            }

        initObservers()
        initDao()

        readAllData.observe(
            viewLifecycleOwner
        ) { user ->
            mViewModel.playerList.value = user
        }

        return binding.root
    }

    fun initDao() {
        repository = UserRepository(mViewModel.initDatabase)
        readAllData = repository.readAllData
    }

    private fun initObservers() {
        mViewModel.apply {
            observeEvent(event, ::handleEvent)
        }
    }

    private fun handleEvent(event: NightEvent) {
        when (event) {
            NightEvent.OnNextClick -> {
                onNextClick()
            }
            NightEvent.OnTestsClick -> {
                nTestsClick()
            }
            is NightEvent.KillPlayer -> {
                killPlayer(event.userId)
            }
        }
    }

    private fun onNextClick() {
        mViewModel.playerList.value?.let {
            Log.i("RMRM", "RMRM " + "onResume() called with: it = $it")
        }
    }

    private fun nTestsClick() {
        lifecycleScope.launch {
            mViewModel.playerList.value?.forEach {
                mViewModel.initDatabase.updateIsPlayerDead(it.id, false)
            }
        }
    }

    private fun killPlayer(userId: Int) {
        lifecycleScope.launch {
            mViewModel.initDatabase.updateIsPlayerDead(userId, true)
        }
    }

}