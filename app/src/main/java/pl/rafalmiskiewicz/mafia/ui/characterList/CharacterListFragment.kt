package pl.rafalmiskiewicz.mafia.ui.characterList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import dagger.hilt.android.AndroidEntryPoint
import pl.rafalmiskiewicz.mafia.databinding.FragmentCharacterBinding
import pl.rafalmiskiewicz.mafia.extensions.observeEvent
import pl.rafalmiskiewicz.mafia.extensions.toast
import pl.rafalmiskiewicz.mafia.ui.base.BaseFragment
import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.db.UserDatabase
import pl.rafalmiskiewicz.mafia.util.db.UserRepository
import javax.inject.Inject

@AndroidEntryPoint
class CharacterListFragment @Inject constructor() : BaseFragment() {

    private val mViewModel: CharacterListViewModel by viewModels()

    private lateinit var binding: FragmentCharacterBinding

    lateinit var readAllData: LiveData<List<User>>
    private lateinit var repository: UserRepository

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

        context?.let {
            initDao(it)
        }
        readAllData.observe(
            viewLifecycleOwner
        ) { user ->
            mViewModel.playerList.value = user
            mViewModel.playersAmount.value = user.size
            mViewModel.setCharacterLeft(user.size)
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

    private fun handleEvent(event: CharacterListEvent) {
        when (event) {
            CharacterListEvent.OnNextClick -> {
                onNextClick()
            }
        }
    }

    private fun onNextClick() {
        if (checkIsNumberCharacterCorrect()) {
            toast("Nieprawidłowa liczba postaci")
            return
        }

    }

    private fun checkIsNumberCharacterCorrect(): Boolean {
        val list = ArrayList(mViewModel.characterPlayerList.value)
        return (((mViewModel.playersAmount.value?.minus(list.sumOf { it.count })) ?: -1) != 0)
    }
}