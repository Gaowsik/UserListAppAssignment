package com.example.assignmentuserlist.user

import com.example.assignmentuserlist.data.APIResource
import com.example.assignmentuserlist.domain.User
import com.example.assignmentuserlist.domain.UserRepository
import com.example.assignmentuserlist.presentation.user.UserViewModel
import com.example.assignmentuserlist.utils.BaseUnitTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class UserViewModelShould : BaseUnitTest() {

    val userCount = 20
    val repository = mock<UserRepository>()
    private val userLists = mock<List<User>>()
    private val expected = APIResource.Success(userLists)
    private val errorBody =
        ResponseBody.create("application/json".toMediaType(), "Something went wrong")
    private val exception = APIResource.Error(true, 400, errorBody)
    private val errorMessage = "Network Error"


    @Test
    fun getUserlistsRepository() = runTest {
        val viewmodel = mockSuccessfulCase()

        viewmodel.getAllUsers(userCount)
        advanceUntilIdle()

        verify(repository, times(1)).getUsers(userCount)
    }

    @Test
    fun emitUserFromRepository() = runTest {
        val viewmodel = mockSuccessfulCase()

        viewmodel.getAllUsers(userCount)
        advanceUntilIdle()

        val value = viewmodel.userListState.first()
        assertEquals(userLists, value)
    }

    @Test
    fun emitErrorWhenReceiveError() = runTest {
        val viewmodel = mockErrorCase()
        viewmodel.getAllUsers(userCount)
        val error = viewmodel.errorMessage.first()
        assertEquals(errorMessage, error)
    }


    suspend fun mockSuccessfulCase(): UserViewModel {
        whenever(repository.getUsers(userCount)).thenReturn(
            expected
        )
        return UserViewModel(repository)
    }


    suspend fun mockErrorCase(): UserViewModel {
        whenever(repository.getUsers(userCount)).thenReturn(
            exception
        )

        return UserViewModel(repository)
    }
}