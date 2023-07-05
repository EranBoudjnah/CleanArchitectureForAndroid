package com.mitteloupe.whoami.home.presentation.viewmodel

import com.mitteloupe.whoami.architecture.presentation.BaseViewModelTest
import com.mitteloupe.whoami.coroutine.currentValue
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Disconnected
import com.mitteloupe.whoami.home.domain.usecase.GetConnectionDetailsUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionStateToPresentationMapper
import com.mitteloupe.whoami.home.presentation.mapper.ExceptionToPresentationMapper
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Loading
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest : BaseViewModelTest<HomeViewState, Any, HomeViewModel>() {
    override val expectedInitialState: HomeViewState = Loading

    @Mock
    lateinit var getConnectionDetailsUseCase: GetConnectionDetailsUseCase

    @Mock
    lateinit var connectionStateToPresentationMapper: ConnectionStateToPresentationMapper

    @Mock
    lateinit var exceptionToPresentationMapper: ExceptionToPresentationMapper

    @Before
    fun setUp() {
        classUnderTest = HomeViewModel(
            getConnectionDetailsUseCase,
            connectionStateToPresentationMapper,
            exceptionToPresentationMapper,
            useCaseExecutor
        )
    }

    @Test
    fun `Given disconnected when onEnter then presents disconnected state`() = runBlocking {
        // Given
        val givenConnectionState = Disconnected
        givenSuccessfulUseCaseExecution(
            getConnectionDetailsUseCase,
            givenConnectionState
        )
        val expectedViewState = HomeViewState.Disconnected
        given { connectionStateToPresentationMapper.toPresentation(givenConnectionState) }
            .willReturn(expectedViewState)

        // When
        classUnderTest.onEnter()
        val actualValue = classUnderTest.viewState.currentValue()

        // Then
        assertEquals(expectedViewState, actualValue)
    }
}
