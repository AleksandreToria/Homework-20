package com.example.homework20.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework20.domain.local.usecase.database.UsersUseCase
import com.example.homework20.domain.local.usecase.validator.EmailValidatorUseCase
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
    private val usersUseCase: UsersUseCase,
    private val emailValidatorUseCase: EmailValidatorUseCase
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
            is UsersEvents.ValidateForm -> validateForm(event.users)
        }
    }

    private fun insertItem(users: Users) {
        viewModelScope.launch {
            if (!validateForm(users)) {
                return@launch
            }
            val userCount = usersUseCase.checkUserUseCase(users.email)
            if (userCount == 0) {
                usersUseCase.insertUserUseCase(users.toDomain())
                _uiEvent.emit(UsersUiEvent.ShowErrorMessage("User added successfully"))
                _uiEvent.emit(UsersUiEvent.ShowStatusMessage(isSuccess = true))
                updateUsersCount()
            } else {
                _uiEvent.emit(UsersUiEvent.ShowErrorMessage("User already exists"))
                _uiEvent.emit(UsersUiEvent.ShowStatusMessage(isSuccess = false))
            }
        }
    }


    private fun removeUser(users: Users) {
        viewModelScope.launch {
            if (!validateForm(users)) {
                return@launch
            }
            val userCount = usersUseCase.checkUserUseCase(users.email)
            if (userCount > 0) {
                usersUseCase.removeUserUseCase(users.toDomain())
                _uiEvent.emit(UsersUiEvent.ShowErrorMessage("User deleted successfully"))
                _uiEvent.emit(UsersUiEvent.ShowStatusMessage(isSuccess = true))
                updateUsersCount()
                _userState.update { currentState ->
                    currentState.copy(removeUser = users)
                }
            } else {
                _uiEvent.emit(UsersUiEvent.ShowErrorMessage("User does not exist"))
                _uiEvent.emit(UsersUiEvent.ShowStatusMessage(isSuccess = false))
            }
        }
    }

    private fun updateUser(users: Users) {
        viewModelScope.launch {
            if (!validateForm(users)) {
                return@launch
            }
            val userCount = usersUseCase.checkUserUseCase(users.email)
            if (userCount > 0) {
                usersUseCase.updateUserUseCase(users.toDomain())
                _uiEvent.emit(UsersUiEvent.ShowErrorMessage("User updated successfully"))
                _uiEvent.emit(UsersUiEvent.ShowStatusMessage(isSuccess = true))
                _userState.update { currentState ->
                    currentState.copy(updateUser = users)
                }
            } else {
                _uiEvent.emit(UsersUiEvent.ShowErrorMessage("User does not exist"))
                _uiEvent.emit(UsersUiEvent.ShowStatusMessage(isSuccess = false))
            }
        }
    }

    private fun validateForm(users: Users): Boolean {
        val isEmailValid = emailValidatorUseCase(users.email)
        val isFirstNameValid = users.firstName.isNotBlank()
        val isLastNameValid = users.lastName.isNotBlank()
        val isAgeValid = users.age.toString().toIntOrNull() != null && users.age > 0

        val areFieldsValid = isEmailValid && isFirstNameValid && isLastNameValid && isAgeValid

        if (!areFieldsValid) {
            val errorMessage =
                buildErrorMessage(isEmailValid, isFirstNameValid, isLastNameValid, isAgeValid)
            viewModelScope.launch {
                _uiEvent.emit(UsersUiEvent.ShowErrorMessage(errorMessage))
            }
        }

        return areFieldsValid
    }

    private fun buildErrorMessage(
        isEmailValid: Boolean,
        isFirstNameValid: Boolean,
        isLastNameValid: Boolean,
        isAgeValid: Boolean
    ): String {
        return buildString {
            if (!isEmailValid) append("Invalid email. ")
            if (!isFirstNameValid) append("First name cannot be empty. ")
            if (!isLastNameValid) append("Last name cannot be empty. ")
            if (!isAgeValid) append("Age must be a number greater than 0.")
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
        data class ShowStatusMessage(val isSuccess: Boolean) : UsersUiEvent
        data class ShowErrorMessage(val message: String) : UsersUiEvent
    }

}