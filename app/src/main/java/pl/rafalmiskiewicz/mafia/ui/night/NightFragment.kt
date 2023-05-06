package pl.rafalmiskiewicz.mafia.ui.night

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
                mViewModel.playerList.value?.find { it.user.id == user.id }?.let {
                    UserWitchCheckBox(
                        isSelected = it.isSelected,
                        user = user
                    )
                } ?: UserWitchCheckBox(
                    isSelected = false,
                    user = user
                )
            }
        }

        return binding.root
    }

    private fun initInstructions() {
        mViewModel.characterMap[mViewModel.charactersListInPlay[mViewModel.characterPointerTurn]]
            ?.let { character ->
                binding.instructionForGameMasetr.text = character.instruction
            }
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
            initInstructions()
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
                val correct = character.makeSpecialAction(idSelectedUsers)
                if (!correct) {
                    Toast.makeText(
                        requireContext(),
                        "Nie udało się wykonać akcji",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else{
                    mViewModel.characterPointerTurn++
                }
            }
    }

    private fun nTestsClick() {

    }

    override fun onItemClick(userId: Int, isSelected: Boolean) {
        mViewModel.playerList.value?.find { it.user.id == userId }?.let {
            it.isSelected = isSelected
            // mAdapter.notifyItemChanged(userId)
        }
    }

}