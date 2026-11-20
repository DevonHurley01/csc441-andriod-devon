package edu.lemoyne.campusapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val loading: Boolean = false,
    val failed: Boolean = false,
)

class LoginViewModel: ViewModel() {
    private val repository = ShowcaseRepository()
    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    fun login(email: String, password: String, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            _state.value = LoginUiState(loading = true)
            try {
                val token = repository.signIn(email, password)
                _state.value = LoginUiState(loading = false)
                onSuccess(token)
            }catch (error: Exception){

            }
        }
    }
}