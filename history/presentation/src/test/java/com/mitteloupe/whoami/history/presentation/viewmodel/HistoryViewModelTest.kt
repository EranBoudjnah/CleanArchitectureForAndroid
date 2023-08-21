package com.mitteloupe.whoami.history.presentation.viewmodel

import com.mitteloupe.whoami.architecture.presentation.BaseViewModelTest
import com.mitteloupe.whoami.coroutine.currentValue
import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel
import com.mitteloupe.whoami.history.domain.usecase.GetHistoryUseCase
import com.mitteloupe.whoami.history.presentation.mapper.SavedIpAddressRecordToPresentationMapper
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.HistoryRecords
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.NoChange
import com.mitteloupe.whoami.history.presentation.model.SavedIpAddressRecordPresentationModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.hamcrest.core.IsIterableContaining.hasItem
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
class HistoryViewModelTest : BaseViewModelTest<HistoryViewState, Any, HistoryViewModel>() {
    override val expectedInitialState: HistoryViewState = NoChange

    @Mock
    private lateinit var getHistoryUseCase: GetHistoryUseCase

    @Mock
    private lateinit var ipRecordToPresentationMapper: SavedIpAddressRecordToPresentationMapper

    @Before
    fun setUp() {
        classUnderTest = HistoryViewModel(
            getHistoryUseCase,
            ipRecordToPresentationMapper,
            useCaseExecutor
        )
    }

    @Test
    fun `Given history when onEnter then presents history`() = runBlocking {
        // Given
        val givenIpAddressRecord1 = domainHistoryRecord("0.0.0.0")
        val givenIpAddressRecord2 = domainHistoryRecord("1.1.1.1")
        val expectedSavedRecords = expectedSavedRecords(
            givenIpAddressRecord1 = givenIpAddressRecord1,
            givenIpAddressRecord2 = givenIpAddressRecord2
        )
        val givenHistory = setOf(givenIpAddressRecord1, givenIpAddressRecord2)
        givenSuccessfulUseCaseExecution(getHistoryUseCase, givenHistory)
        val expectedViewState = HistoryRecords(expectedSavedRecords)

        // When
        classUnderTest.onEnter()
        val actualValue = classUnderTest.viewState.currentValue()

        // Then
        assertThat(actualValue, instanceOf(expectedViewState::class.java))
        assertThat(
            (actualValue as HistoryRecords).historyRecords,
            hasSize(expectedSavedRecords.size)
        )
        expectedSavedRecords.forEach { expectedRecord ->
            assertThat(actualValue.historyRecords, hasItem(expectedRecord))
        }
    }

    private fun expectedSavedRecords(
        givenIpAddressRecord1: SavedIpAddressRecordDomainModel,
        givenIpAddressRecord2: SavedIpAddressRecordDomainModel
    ): Collection<SavedIpAddressRecordPresentationModel> {
        val expectedIpAddressRecord1 = presentationHistoryRecord("0_0_0_0")
        given(ipRecordToPresentationMapper.toPresentation(givenIpAddressRecord1))
            .willReturn(expectedIpAddressRecord1)

        val expectedIpAddressRecord2 = presentationHistoryRecord("1-1-1-1")
        given(ipRecordToPresentationMapper.toPresentation(givenIpAddressRecord2))
            .willReturn(expectedIpAddressRecord2)

        return setOf(expectedIpAddressRecord1, expectedIpAddressRecord2)
    }

    private fun domainHistoryRecord(ipAddress: String) = SavedIpAddressRecordDomainModel(
        ipAddress,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        321L
    )

    private fun presentationHistoryRecord(ipAddress: String) =
        SavedIpAddressRecordPresentationModel(
            ipAddress,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            123L
        )
}
