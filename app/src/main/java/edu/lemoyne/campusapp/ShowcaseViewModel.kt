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

    private var accessToken: String? = null

    fun setAccessToken(token: String) {
        accessToken = token
    }
    fun refresh() {
        val token = accessToken ?: run {
            _uiState.value = ShowcaseUiState.Error("Please Log in first")
        }
        viewModelScope.launch {
            _uiState.value = ShowcaseUiState.Loading
            try {
                val items = repository.loadItems(token as String)
                _uiState.value = ShowcaseUiState.Success(items)
            }catch (error: Exception) {
                _uiState.value = ShowcaseUiState.Error(error.message ?: "Network error")
            }
        }
    }
}