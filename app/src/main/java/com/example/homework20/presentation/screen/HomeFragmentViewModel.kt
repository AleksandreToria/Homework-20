package com.example.homework20.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework20.domain.local.usecase.UsersUseCase
import com.example.homework20.presentation.event.UsersEvents
import com.example.homework20.presentation.mapper.toDomain
import com.example.homework20.presentation.model.Users
import com.example.homework20.presentation.state.UsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow(UsersState())
    val userState: StateFlow<UsersState> = _userState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UsersUiEvent>()
    val uiEvent: SharedFlow<UsersUiEvent> get() = _uiEvent

    fun onEvent(event: UsersEvents) {
        when (event) {
            is UsersEvents.InsertUser -> insertItem(event.users)
            is UsersEvents.RemoveUser -> removeUser(event.users)
            is UsersEvents.UpdateUser -> updateUser(event.users)
            UsersEvents.CountUsers -> updateUsersCount()
        }
    }

    private fun insertItem(users: Users) {
        viewModelScope.launch {
            usersUseCase.insertUserUseCase(users.toDomain())

            updateUsersCount()

            _userState.update { currentState ->
                currentState.copy(insertUser = users)
            }
        }
    }

    private fun removeUser(users: Users) {
        viewModelScope.launch {
            usersUseCase.removeUserUseCase(users.toDomain())

            updateUsersCount()

            _userState.update { currentState ->
                currentState.copy(removeUser = users)
            }
        }
    }

    private fun updateUser(users: Users) {
        viewModelScope.launch {
            usersUseCase.updateUserUseCase(users.toDomain())

            _userState.update { currentState ->
                currentState.copy(updateUser = users)
            }
        }
    }

    private fun updateUsersCount() {
        viewModelScope.launch {
            _userState.update { currentState ->
                currentState.copy(countUsers = usersUseCase.countUsersUseCase())
            }
        }
    }

    sealed interface UsersUiEvent {
        data object rame : UsersUiEvent
    }
}