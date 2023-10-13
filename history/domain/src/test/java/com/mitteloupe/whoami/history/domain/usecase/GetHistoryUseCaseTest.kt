package com.mitteloupe.whoami.history.domain.usecase

import com.mitteloupe.whoami.coroutine.currentValue
import com.mitteloupe.whoami.coroutine.fakeCoroutineContextProvider
import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel
import com.mitteloupe.whoami.history.domain.repository.GetHistoryRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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

    @Before
    fun setUp() {
        classUnderTest = GetHistoryUseCase(getHistoryRepository, fakeCoroutineContextProvider)
    }

    @Test
    fun `Given IP addresses history when executeInBackground then returns history records`() = runTest {
        // Given
        val expectedHistory = listOf(
            savedIpAddressRecord("1.2.3.4"),
            savedIpAddressRecord("4.3.2.1")
        )
        given(getHistoryRepository.history()).willReturn(flowOf(expectedHistory))

        // When
        val actualHistory = classUnderTest.executeInBackground(Unit).currentValue()

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
