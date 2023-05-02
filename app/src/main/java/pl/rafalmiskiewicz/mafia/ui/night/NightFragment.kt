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
import javax.inject.Inject

@AndroidEntryPoint
class NightFragment @Inject constructor(
) : BaseFragment() {

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
                playerCharacterListRecycle.adapter = NightAdapter(mViewModel.characterMap)
            }

        initCharactersListInPlay()
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

    fun initCharactersListInPlay() {
        mViewModel.playerList.observe(viewLifecycleOwner) {
            mViewModel.charactersListInPlay = it.map {user ->
                Log.i("RMRM", "RMRM "+"initCharactersListInPlay() called with: user = $user")
                val characterId = user.character
                val priority = mViewModel.characterMap.get(user.character)?.prority
                Pair(characterId, priority)
            }.sortedBy { it.second }
                .map { it.first }
                .distinct()
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
                makeSpecialActionCharacter(event.userId, event.userId)
                //killPlayer(event.userId)
            }
        }
    }

    private fun makeSpecialActionCharacter(userId: Int, chosenId: Int) {
        mViewModel.playerList.value?.find { it.id == userId }?.let {
            mViewModel.characterMap.get(it.character)?.makeSpecificAction(chosenId - 1)
        }
    }

    private fun onNextClick() {

    }

    private fun nTestsClick() {

    }

    private fun killPlayer(userId: Int) {
        lifecycleScope.launch {
            mViewModel.initDatabase.updateIsPlayerDead(userId, true)
        }
    }

}