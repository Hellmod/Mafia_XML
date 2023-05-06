package pl.rafalmiskiewicz.mafia.ui.night

import android.os.Bundle
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
import pl.rafalmiskiewicz.mafia.util.db.UserWitchCheckBox
import javax.inject.Inject

@AndroidEntryPoint
class NightFragment @Inject constructor(
) : BaseFragment(), CheckPlayerListener {

    private val mViewModel: NightViewModel by viewModels()
    private lateinit var mAdapter: NightAdapter

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
            }

        initCharactersListInPlay()
        initObservers()
        initDao()
        initAdapter()

        readAllData.observe(
            viewLifecycleOwner
        ) { user ->
            mViewModel.playerList.value = user.map { user ->
                UserWitchCheckBox(
                    isSelected = false,
                    user = user
                )
            }
        }

        return binding.root
    }

    private fun initAdapter() {
        mAdapter = NightAdapter(
            characterMap = mViewModel.characterMap,
            checkPlayerListener = this@NightFragment
        )
        binding.playerCharacterListRecycle.adapter = mAdapter
        mViewModel.playerList.observe(viewLifecycleOwner) { newList ->
            requireActivity().runOnUiThread {
                mAdapter.updateData(newList)
            }
        }
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
            mViewModel.charactersListInPlay = it.map { user ->
                val characterId = user.user.character
                val priority = mViewModel.characterMap.get(user.user.character)?.prority
                Pair(characterId, priority)
            }.sortedBy { it.second }
                .map { it.first }
                .distinct()
        }
    }

    private fun handleEvent(event: NightEvent) {
        when (event) {
            is NightEvent.OnNextClick -> {
                makeSpecialActionCharacter(event.idSelectedUsers)
            }

            NightEvent.OnTestsClick -> {
                nTestsClick()
            }
        }
    }

    private fun makeSpecialActionCharacter(idSelectedUsers: List<Int>) {
        mViewModel.characterMap[mViewModel.charactersListInPlay[mViewModel.characterPointerTurn]]
            ?.let { character ->
                character.makeSpecialAction(idSelectedUsers)
            }
    }

    private fun nTestsClick() {

    }

    private fun killPlayer(userId: Int) {
        lifecycleScope.launch {
            mViewModel.initDatabase.updateIsPlayerDead(userId, true)
        }
    }

    override fun onItemClick(userId: Int, isSelected: Boolean) {
        mViewModel.playerList.value?.find { it.user.id == userId }?.let {
            it.isSelected = isSelected
        }
    }

}