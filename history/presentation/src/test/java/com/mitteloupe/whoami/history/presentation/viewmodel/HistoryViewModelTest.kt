package com.mitteloupe.whoami.history.presentation.viewmodel

import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import com.mitteloupe.whoami.architecture.presentation.viewmodel.BaseViewModelTest
import com.mitteloupe.whoami.history.domain.model.HistoryRecordDeletionDomainModel
import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel
import com.mitteloupe.whoami.history.domain.usecase.DeleteHistoryRecordUseCase
import com.mitteloupe.whoami.history.domain.usecase.GetHistoryUseCase
import com.mitteloupe.whoami.history.presentation.mapper.DeleteHistoryRecordRequestDomainMapper
import com.mitteloupe.whoami.history.presentation.mapper.SavedIpAddressRecordPresentationMapper
import com.mitteloupe.whoami.history.presentation.model.HistoryRecordDeletionPresentationModel
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.HistoryRecords
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.Loading
import com.mitteloupe.whoami.history.presentation.model.SavedIpAddressRecordPresentationModel
import kotlinx.coroutines.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.hamcrest.core.IsIterableContaining.hasItem
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.given
import org.mockito.kotlin.verify

private const val STATE_SETTLING_DELAY_MILLISECONDS = 10L

@RunWith(MockitoJUnitRunner::class)
class HistoryViewModelTest :
    BaseViewModelTest<HistoryViewState, PresentationNotification, HistoryViewModel>() {
    @Mock
    private lateinit var getHistoryUseCase: GetHistoryUseCase

    @Mock
    private lateinit var savedRecordPresentationMapper: SavedIpAddressRecordPresentationMapper

    @Mock
    private lateinit var deleteHistoryRecordUseCase: DeleteHistoryRecordUseCase

    @Mock
    private lateinit var deleteRecordRequestDomainMapper: DeleteHistoryRecordRequestDomainMapper

    @Before
    fun setUp() {
        classUnderTest = HistoryViewModel(
            getHistoryUseCase,
            savedRecordPresentationMapper,
            deleteHistoryRecordUseCase,
            deleteRecordRequestDomainMapper,
            useCaseExecutor
        )
    }

    @Test
    fun `Given history when onEnter then presents history`() {
        runBlocking {
            // Given
            val givenIpAddressRecord1 = domainHistoryRecord("0.0.0.0")
            val givenIpAddressRecord2 = domainHistoryRecord("1.1.1.1")
            val expectedSavedRecords = expectedSavedRecords(
                givenIpAddressRecord1 = givenIpAddressRecord1,
                givenIpAddressRecord2 = givenIpAddressRecord2
            )
            val givenHistory = setOf(givenIpAddressRecord1, givenIpAddressRecord2)
            getHistoryUseCase.givenSuccessfulExecution(givenHistory)
            val highlightedIpAddress: String? = null

            val deferredViewState = async(start = UNDISPATCHED) {
                classUnderTest.viewState.take(2).toList()
            }

            // When
            classUnderTest.onEnter(highlightedIpAddress)
            delay(STATE_SETTLING_DELAY_MILLISECONDS)
            val actualValue = deferredViewState.await()

            // Then
            assertEquals(Loading, actualValue[0])
            val actualHistoryRecords = actualValue[1] as HistoryRecords
            assertEquals(
                highlightedIpAddress,
                actualHistoryRecords.highlightedIpAddress
            )
            assertThat(
                actualHistoryRecords.historyRecords,
                hasSize(expectedSavedRecords.size)
            )
            expectedSavedRecords.forEach { expectedRecord ->
                assertThat(actualHistoryRecords.historyRecords, hasItem(expectedRecord))
            }
        }
    }

    @Test
    fun `Given history, highlighted IP when onEnter then presents history with IP`() = runTest {
        // Given
        val givenIpAddressRecord1 = domainHistoryRecord("0.0.0.0")
        val givenIpAddressRecord2 = domainHistoryRecord("1.1.1.1")
        val givenHistory = setOf(givenIpAddressRecord1, givenIpAddressRecord2)
        getHistoryUseCase.givenSuccessfulExecution(givenHistory)
        val highlightedIpAddress = "0.0.0.0"
        val deferredViewState = async(start = UNDISPATCHED) {
            classUnderTest.viewState.take(2).toList()
        }

        // When
        classUnderTest.onEnter(highlightedIpAddress)
        val actualValue = deferredViewState.await()

        // Then
        assertEquals(Loading, actualValue[0])
        assertEquals(
            highlightedIpAddress,
            (actualValue[1] as HistoryRecords).highlightedIpAddress
        )
    }

    @Test
    fun `Given deletion request when onDeleteAction then deletes record`() {
        // Given
        val givenIpAddress = "0.0.0.0"
        val givenDeletionRequest = HistoryRecordDeletionPresentationModel(givenIpAddress)
        val domainDeletionRequest = HistoryRecordDeletionDomainModel(givenIpAddress)
        given(deleteRecordRequestDomainMapper.toDomain(givenDeletionRequest))
            .willReturn(domainDeletionRequest)

        // When
        classUnderTest.onDeleteAction(givenDeletionRequest)

        // Then
        verify(useCaseExecutor).execute(
            useCase = eq(deleteHistoryRecordUseCase),
            value = eq(domainDeletionRequest),
            onResult = any(),
            onException = any()
        )
    }

    private fun expectedSavedRecords(
        givenIpAddressRecord1: SavedIpAddressRecordDomainModel,
        givenIpAddressRecord2: SavedIpAddressRecordDomainModel
    ): Collection<SavedIpAddressRecordPresentationModel> {
        val expectedIpAddressRecord1 = presentationHistoryRecord("0_0_0_0")
        given(savedRecordPresentationMapper.toPresentation(givenIpAddressRecord1))
            .willReturn(expectedIpAddressRecord1)

        val expectedIpAddressRecord2 = presentationHistoryRecord("1-1-1-1")
        given(savedRecordPresentationMapper.toPresentation(givenIpAddressRecord2))
            .willReturn(expectedIpAddressRecord2)

        return setOf(expectedIpAddressRecord1, expectedIpAddressRecord2)
    }

    private fun domainHistoryRecord(ipAddress: String) = SavedIpAddressRecordDomainModel(
        ipAddress = ipAddress,
        city = null,
        region = null,
        countryCode = null,
        geolocation = null,
        internetServiceProviderName = null,
        postCode = null,
        timeZone = null,
        savedAtTimestampMilliseconds = 321L
    )

    private fun presentationHistoryRecord(ipAddress: String) =
        SavedIpAddressRecordPresentationModel(
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
