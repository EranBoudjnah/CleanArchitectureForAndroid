package com.mitteloupe.whoami.history.domain.usecase

import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel
import com.mitteloupe.whoami.history.domain.repository.GetHistoryRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
class GetHistoryUseCaseTest {
    private lateinit var classUnderTest: GetHistoryUseCase

    @Mock
    private lateinit var getHistoryRepository: GetHistoryRepository

    @Mock
    lateinit var coroutineContextProvider: CoroutineContextProvider

    @Before
    fun setUp() {
        classUnderTest = GetHistoryUseCase(getHistoryRepository, coroutineContextProvider)
    }

    @Test
    fun `Given IP addresses history when executeInBackground then returns history records`() {
        // Given
        val expectedHistory = listOf(
            savedIpAddressRecord("1.2.3.4"),
            savedIpAddressRecord("4.3.2.1")
        )
        given(getHistoryRepository.history()).willReturn(expectedHistory)

        // When
        val actualHistory = classUnderTest.executeInBackground(Unit)

        // Then
        assertEquals(expectedHistory, actualHistory)
    }

    private fun savedIpAddressRecord(ipAddress: String) = SavedIpAddressRecordDomainModel(
        ipAddress = ipAddress,
        city = null,
        region = null,
        countryCode = null,
        geolocation = null,
        internetServiceProviderName = null,
        postCode = null,
        timeZone = null,
        savedAtTimestampMilliseconds = 123L
    )
}
