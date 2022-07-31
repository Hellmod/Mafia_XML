package pl.rafalmiskiewicz.mafia.ui.characterList

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.rafalmiskiewicz.mafia.data.common.CharacterItem
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor() : BaseViewModel<CharacterListEvent>() {

    val characterList = MutableLiveData<List<CharacterItem>>()

}