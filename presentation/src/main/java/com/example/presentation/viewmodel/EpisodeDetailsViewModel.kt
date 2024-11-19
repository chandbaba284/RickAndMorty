package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.mapper.CharacterDetailsMapper
import com.example.domain.mapper.EpisodeDetailsMapper
import com.example.domain.usecase.EpisodeDetailsUseCase
import com.example.presentation.uistate.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeDetailsViewModel @Inject constructor(val episodeDetailsUseCase: EpisodeDetailsUseCase) :
    ViewModel() {

    private val _episodeDetails: MutableStateFlow<UiState<EpisodeDetailsMapper>> = MutableStateFlow(
        UiState.Loading
    )
    val episodeDetails: StateFlow<UiState<EpisodeDetailsMapper>> = _episodeDetails

    var episodeTitleForTopBar = ""
        private set


    fun getEpisodeDetailsByUsingEpisodeId(episodeId: String) {
        viewModelScope.launch {
            val result = episodeDetailsUseCase.invoke(episodeId)
            _episodeDetails.emit(UiState.Loading)
            result.onSuccess {
                episodeTitleForTopBar = it.title?:""
                _episodeDetails.emit(UiState.Success(data = it))
            }.onFailure {
               _episodeDetails.emit(UiState.Error(Exception("Episode Details are Empty")))
            }


        }
    }

     fun getEpisodeTitle(): String {
        return episodeTitleForTopBar
    }
}