package com.mitteloupe.whoami.datasource.connection.datasource

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.NetworkRequest
import com.mitteloupe.whoami.coroutine.currentValue
import com.mitteloupe.whoami.coroutine.fakeCoroutineContextProvider
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Connected
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Disconnected
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Unset
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.willAnswer

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ConnectionDataSourceImplTest {
    private lateinit var classUnderTest: ConnectionDataSourceImpl

    @Mock
    private lateinit var connectivityManager: ConnectivityManager

    private lateinit var networkRequestProvider: () -> NetworkRequest

    @Mock
    private lateinit var networkRequest: NetworkRequest

    @Before
    fun setUp() {
        networkRequestProvider = { networkRequest }
    }

    @Test
    fun `Given initial read when observeIsConnected then returns Unset`() = runTest {
        // Given
        givenClassUnderTest()

        // When
        val actualValue = classUnderTest.observeIsConnected().currentValue()

        // Then
        assertEquals(Unset, actualValue)
    }

    @Test
    fun `Given network connection available when observeIsConnected then returns Connected`() =
        runTest {
            // Given
            givenClassUnderTest()

            var networkCallback: NetworkCallback? = null
            given {
                connectivityManager.registerNetworkCallback(
                    eq(networkRequest),
                    any<NetworkCallback>()
                )
            } willAnswer { invocation ->
                networkCallback = invocation.getArgument(1)
            }
            val actualValueFlow = classUnderTest.observeIsConnected()

            // When
            networkCallback!!.onAvailable(mock())
            advanceUntilIdle()
            val actualValue = actualValueFlow.currentValue()

            // Then
            assertEquals(Connected, actualValue)
        }

    @Test
    fun `Given network connection lost when observeIsConnected then returns Disconnected`() =
        runTest {
            // Given
            givenClassUnderTest()

            var networkCallback: NetworkCallback? = null
            given {
                connectivityManager.registerNetworkCallback(
                    eq(networkRequest),
                    any<NetworkCallback>()
                )
            } willAnswer { invocation ->
                networkCallback = invocation.getArgument(1)
            }
            val actualValueFlow = classUnderTest.observeIsConnected()

            // When
            networkCallback!!.onLost(mock())
            advanceUntilIdle()
            val actualValue = actualValueFlow.currentValue()

            // Then
            assertEquals(Disconnected, actualValue)
        }

    private fun TestScope.givenClassUnderTest() {
        classUnderTest = ConnectionDataSourceImpl(
            connectivityManager,
            this,
            fakeCoroutineContextProvider,
            networkRequestProvider
        )
    }
}
