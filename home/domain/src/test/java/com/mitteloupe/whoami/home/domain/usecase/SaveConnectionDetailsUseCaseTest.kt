package com.mitteloupe.whoami.home.domain.usecase

import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Connected
import com.mitteloupe.whoami.home.domain.repository.SaveConnectionDetailsRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class SaveConnectionDetailsUseCaseTest {
    private lateinit var classUnderTest: SaveConnectionDetailsUseCase

    @Mock
    private lateinit var saveConnectionDetailsRepository: SaveConnectionDetailsRepository

    @Mock
    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @Before
    fun setUp() {
        classUnderTest =
            SaveConnectionDetailsUseCase(saveConnectionDetailsRepository, coroutineContextProvider)
    }

    @Test
    fun `Given connection details when executeInBackground then saves connection details`() {
        // Given
        val connectionDetails = Connected(
            ipAddress = "1.2.3.4",
            city = "Paris",
            region = "Paris",
            countryCode = "France",
            geolocation = "0.0,0.0",
            internetServiceProviderName = "Le ISP",
            postCode = "12345",
            timeZone = "GMT +1"
        )

        // When
        classUnderTest.executeInBackground(connectionDetails)

        // Then
        verify(saveConnectionDetailsRepository).saveConnectionDetails(connectionDetails)
    }
}
