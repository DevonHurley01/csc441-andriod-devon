package edu.lemoyne.campusapp


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class  ShowcaseUiState {
    data object Loading: ShowcaseUiState()
    data class Success(val items: List<ShowcaseItem>): ShowcaseUiState()
    data class Error(val message: String): ShowcaseUiState()
}

class ShowcaseViewModel: ViewModel() {
    private val repository = ShowcaseRepository()

    private val _uiState = MutableStateFlow<ShowcaseUiState>(ShowcaseUiState.Loading)
    val uiState: StateFlow<ShowcaseUiState> = _uiState

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = ShowcaseUiState.Loading
            try {
                val items = repository.loadItems()
                _uiState.value = ShowcaseUiState.Success(items)
            }catch (error: Exception) {
                _uiState.value = ShowcaseUiState.Error(error.message ?: "Network error")
            }
        }
    }
}