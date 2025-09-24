package com.example.assignmentuserlist.user

import com.example.assignmentuserlist.data.APIResource
import com.example.assignmentuserlist.data.model.UserResponse
import com.example.assignmentuserlist.data.source.UserApi
import com.example.assignmentuserlist.data.source.UserDataSourceImpl
import com.example.assignmentuserlist.domain.User
import com.example.assignmentuserlist.utils.BaseUnitTest
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.whenever
import retrofit2.Response

class UserDataSourceShould : BaseUnitTest() {


    val userCount = 20
    private val userAPI = mock<UserApi>()
    private val userLists = mock<List<User>>()
    private val userResponse = mock<UserResponse>()
    private val expected = APIResource.Success(userLists)
    private val errorBody =
        ResponseBody.create("application/json".toMediaType(), "Something went wrong")
    val errorResponse = Response.error<String>(
        400, ResponseBody.create("application/json".toMediaType(), "Something went wrong")
    )


    @Test
    fun getUserlistFromAPI() = runTest {
        val dataSource = mockSuccessfulCase()
        val result = dataSource.getUsers(userCount)
        Mockito.verify(userAPI, times(1)).getUsers(userCount)
        assertTrue(result is APIResource.Success)
    }

    @Test
    fun propogateErrors() = runTest {
        val dataSource = mockFailureCase()
        val result = dataSource.getUsers(userCount)

        assertTrue(result is APIResource.Error)
        result as APIResource.Error
        assertEquals(false, result.isNetworkError)
        assertEquals(400, result.errorCode)
        assertEquals("Something went wrong", result.errorBody?.string())
    }


    private suspend fun mockSuccessfulCase(): UserDataSourceImpl {

        whenever(userAPI.getUsers(userCount)).thenReturn(
            userResponse
        )

        return UserDataSourceImpl(userAPI)
    }

    private suspend fun mockFailureCase(): UserDataSourceImpl {

        whenever(userAPI.getUsers(userCount)).thenThrow(
            retrofit2.HttpException(errorResponse)
        )
        return UserDataSourceImpl(userAPI)
    }


}