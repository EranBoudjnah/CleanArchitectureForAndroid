package com.mitteloupe.whoami.home.domain.usecase

import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import com.mitteloupe.whoami.home.domain.repository.GetConnectionDetailsRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
class GetConnectionDetailsUseCaseTest {
    private lateinit var classUnderTest: GetConnectionDetailsUseCase

    @Mock
    private lateinit var getConnectionDetailsRepository: GetConnectionDetailsRepository

    @Mock
    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @Before
    fun setUp() {
        classUnderTest =
            GetConnectionDetailsUseCase(getConnectionDetailsRepository, coroutineContextProvider)
    }

    @Test
    fun `Given connection details when executeInBackground then returns connection details`() =
        runBlocking {
            // Given
            val expectedConnectionDetails = ConnectionDetailsDomainModel.Disconnected
            given { getConnectionDetailsRepository.connectionDetails() }
                .willReturn(flowOf(expectedConnectionDetails))

            // When
            val actualValue = classUnderTest.executeInBackground(Unit).lastOrNull()

            // Then
            assertEquals(expectedConnectionDetails, actualValue)
        }
}
