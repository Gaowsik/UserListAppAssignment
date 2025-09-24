package com.example.assignmentuserlist.user

import com.example.assignmentuserlist.data.APIResource
import com.example.assignmentuserlist.data.repository.UserRepositoryImpl
import com.example.assignmentuserlist.data.source.UserDataSource
import com.example.assignmentuserlist.domain.User
import com.example.assignmentuserlist.utils.BaseUnitTest
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.whenever

class UserRepositoryShould : BaseUnitTest() {
    val userCount = 20
    private val dataSource = mock<UserDataSource>()
    val repository = UserRepositoryImpl(dataSource)
    private val userLists = mock<List<User>>()

    private val expected = APIResource.Success(userLists)
    private val errorBody =
        ResponseBody.create("application/json".toMediaType(), "Something went wrong")
    private val exception = APIResource.Error(true, 400, errorBody)


    @Test
    fun getPlaylistFromDataSource() = runTest {
        val repository = mockSuccessfulCase()
        repository.getUsers(userCount)
        Mockito.verify(dataSource, times(1)).getUsers(userCount)
        assertEquals(expected, repository.getUsers(userCount))
    }

    @Test
    fun propogateErrors() = runTest {
        val repository = mockFailureCase()
        assertEquals(
            exception,
            repository.getUsers(userCount)
        )

    }


    private suspend fun mockSuccessfulCase(): UserRepositoryImpl {
        whenever(dataSource.getUsers(userCount)).thenReturn(
            expected
        )

        return UserRepositoryImpl(dataSource)
    }

    private suspend fun mockFailureCase(): UserRepositoryImpl {

        whenever(dataSource.getUsers(userCount)).thenReturn(
            exception
        )
        return UserRepositoryImpl(dataSource)
    }

}