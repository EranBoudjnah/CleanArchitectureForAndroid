package com.mitteloupe.whoami.home.presentation.viewmodel

import com.mitteloupe.whoami.architecture.presentation.BaseViewModelTest
import com.mitteloupe.whoami.coroutine.currentValue
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Disconnected
import com.mitteloupe.whoami.home.domain.usecase.GetConnectionDetailsUseCase
import com.mitteloupe.whoami.home.domain.usecase.SaveConnectionDetailsUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsToDomainMapper
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionStateToPresentationMapper
import com.mitteloupe.whoami.home.presentation.mapper.ExceptionToPresentationMapper
import com.mitteloupe.whoami.home.presentation.model.HomeNotification
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Loading
import com.mitteloupe.whoami.home.presentation.navigation.ViewHistoryPresentationDestination
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
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
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest : BaseViewModelTest<HomeViewState, HomeNotification, HomeViewModel>() {
    override val expectedInitialState: HomeViewState = Loading

    @Mock
    private lateinit var getConnectionDetailsUseCase: GetConnectionDetailsUseCase

    @Mock
    private lateinit var connectionStateToPresentationMapper: ConnectionStateToPresentationMapper

    @Mock
    private lateinit var saveConnectionDetailsUseCase: SaveConnectionDetailsUseCase

    @Mock
    private lateinit var connectionDetailsToDomainMapper: ConnectionDetailsToDomainMapper

    @Mock
    private lateinit var exceptionToPresentationMapper: ExceptionToPresentationMapper

    @Before
    fun setUp() {
        classUnderTest = HomeViewModel(
            getConnectionDetailsUseCase,
            connectionStateToPresentationMapper,
            saveConnectionDetailsUseCase,
            connectionDetailsToDomainMapper,
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

    @Test
    fun `Given connection details when onSaveDetails then details saved, notifies on success`() =
        runTest {
            val ipAddress = "1.3.3.7"
            val givenConnectionDetails = HomeViewState.Connected(
                ipAddress = ipAddress,
                city = null,
                region = null,
                countryCode = null,
                geolocation = null,
                internetServiceProviderName = null,
                postCode = null,
                timeZone = null
            )
            val domainConnectionDetails = ConnectionDetailsDomainModel.Connected(
                ipAddress = ipAddress,
                city = null,
                region = null,
                countryCode = null,
                geolocation = null,
                internetServiceProviderName = null,
                postCode = null,
                timeZone = null
            )
            given { connectionDetailsToDomainMapper.toDomain(givenConnectionDetails) }
                .willReturn(domainConnectionDetails)
            givenSuccessfulNoResultUseCaseExecution(
                saveConnectionDetailsUseCase,
                domainConnectionDetails
            )
            val deferredNotification = async(start = UNDISPATCHED) {
                classUnderTest.notification.first()
            }
            val expectedNotification = HomeNotification.ConnectionSaved(ipAddress)

            // When
            classUnderTest.onSaveDetails(givenConnectionDetails)
            val actualNotification = deferredNotification.await()

            // Then
            verify(useCaseExecutor).execute(
                useCase = eq(saveConnectionDetailsUseCase),
                value = eq(domainConnectionDetails),
                onResult = any(),
                onException = any()
            )
            assertEquals(expectedNotification, actualNotification)
        }

    @Test
    fun `When onViewHistoryAction then navigates to View History`() = runTest {
        val deferredDestination = async(start = UNDISPATCHED) {
            classUnderTest.destination.first()
        }
        val expectedDestination = ViewHistoryPresentationDestination

        // When
        classUnderTest.onViewHistoryAction()
        val actualDestination = deferredDestination.await()

        // Then
        assertEquals(expectedDestination, actualDestination)
    }
}
