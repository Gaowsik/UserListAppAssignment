package com.example.assignmentuserlist.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentuserlist.data.APIResource
import com.example.assignmentuserlist.domain.User
import com.example.assignmentuserlist.domain.UserRepository
import com.example.assignmentuserlist.utils.parseErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _userListState = MutableStateFlow<List<User>>(emptyList())
    val userListState: StateFlow<List<User>> = _userListState.asStateFlow()

    fun getUserById(userId: String): Flow<User?> =
        _userListState.map { list -> list.find { it.userId == userId } }


    fun getAllUsers() {
        viewModelScope.launch {
            setLoading(true)
            val response = userRepository.getUsers()
            when (response) {
                is APIResource.Success -> {
                    setLoading(false)
                    response.value.let {
                        _userListState.value = it
                    }

                }

                is APIResource.Loading -> {
                    setLoading(true)
                }

                is APIResource.Error -> {
                    setLoading(false)
                    _errorMessage.emit(parseErrors(response))
                }

                else -> {

                }

            }
        }
    }

    fun setLoading(value: Boolean) {
        _isLoading.value = value
    }


}