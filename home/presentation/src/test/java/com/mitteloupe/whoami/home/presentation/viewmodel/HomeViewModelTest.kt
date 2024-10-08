package com.mitteloupe.whoami.home.presentation.viewmodel

import com.mitteloupe.whoami.architecture.presentation.viewmodel.BaseViewModelTest
import com.mitteloupe.whoami.coroutine.currentValue
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Disconnected
import com.mitteloupe.whoami.home.domain.usecase.GetConnectionDetailsUseCase
import com.mitteloupe.whoami.home.domain.usecase.SaveConnectionDetailsUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsToDomainMapper
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionStateToPresentationMapper
import com.mitteloupe.whoami.home.presentation.mapper.ExceptionToPresentationMapper
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Loading
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnSavedDetails
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewHistory
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewOpenSourceNotices
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
class HomeViewModelTest :
    BaseViewModelTest<HomeViewState, HomePresentationNotification, HomeViewModel>() {
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
    fun `Given connection details when onSaveDetailsAction then details saved, notifies success`() =
        runTest {
            val ipAddress = "1.3.3.7"
            val givenConnectionDetails = viewStateConnected(ipAddress)
            val domainConnectionDetails = domainConnectionDetails(ipAddress)
            given { connectionDetailsToDomainMapper.toDomain(givenConnectionDetails) }
                .willReturn(domainConnectionDetails)
            givenSuccessfulNoResultUseCaseExecution(
                saveConnectionDetailsUseCase,
                domainConnectionDetails
            )
            val deferredNotification = async(start = UNDISPATCHED) {
                classUnderTest.notification.first()
            }
            val expectedNotification = HomePresentationNotification.ConnectionSaved(ipAddress)

            // When
            classUnderTest.onSaveDetailsAction(givenConnectionDetails)
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
    fun `Given connection details when onSaveDetailsAction then details saved`() = runTest {
        val ipAddress = "1.3.3.7"
        val givenConnectionDetails = viewStateConnected(ipAddress)
        val domainConnectionDetails = domainConnectionDetails(ipAddress)
        given { connectionDetailsToDomainMapper.toDomain(givenConnectionDetails) }
            .willReturn(domainConnectionDetails)
        givenSuccessfulNoResultUseCaseExecution(
            saveConnectionDetailsUseCase,
            domainConnectionDetails
        )

        // When
        classUnderTest.onSaveDetailsAction(givenConnectionDetails)

        // Then
        verify(useCaseExecutor).execute(
            useCase = eq(saveConnectionDetailsUseCase),
            value = eq(domainConnectionDetails),
            onResult = any(),
            onException = any()
        )
    }

    @Test
    fun `Given connection details when onSaveDetailsAction then notifies success`() = runTest {
        val ipAddress = "192.168.0.1"
        val givenConnectionDetails = viewStateConnected(ipAddress)
        val domainConnectionDetails = domainConnectionDetails(ipAddress)
        given { connectionDetailsToDomainMapper.toDomain(givenConnectionDetails) }
            .willReturn(domainConnectionDetails)
        givenSuccessfulNoResultUseCaseExecution(
            saveConnectionDetailsUseCase,
            domainConnectionDetails
        )
        val deferredNotification = async(start = UNDISPATCHED) {
            classUnderTest.notification.first()
        }
        val expectedNotification = HomePresentationNotification.ConnectionSaved(ipAddress)

        // When
        classUnderTest.onSaveDetailsAction(givenConnectionDetails)
        val actualNotification = deferredNotification.await()

        // Then
        assertEquals(expectedNotification, actualNotification)
    }

    @Test
    fun `Given connection details when onSaveDetailsAction then emits OnSavedDetails event`() =
        runTest {
            val ipAddress = "1.1.1.1"
            val givenConnectionDetails = viewStateConnected(ipAddress)
            val domainConnectionDetails = domainConnectionDetails(ipAddress)
            given { connectionDetailsToDomainMapper.toDomain(givenConnectionDetails) }
                .willReturn(domainConnectionDetails)
            givenSuccessfulNoResultUseCaseExecution(
                saveConnectionDetailsUseCase,
                domainConnectionDetails
            )
            val deferredNavigationEvent = async(start = UNDISPATCHED) {
                classUnderTest.navigationEvent.first()
            }
            val expectedNavigationEvent = OnSavedDetails(ipAddress)

            // When
            classUnderTest.onSaveDetailsAction(givenConnectionDetails)
            val actualNavigationEvent = deferredNavigationEvent.await()

            // Then
            assertEquals(expectedNavigationEvent, actualNavigationEvent)
        }

    @Test
    fun `When onViewHistoryAction then emits OnViewHistory event`() = runTest {
        val deferredNavigationEvent = async(start = UNDISPATCHED) {
            classUnderTest.navigationEvent.first()
        }
        val expectedNavigationEvent = OnViewHistory

        // When
        classUnderTest.onViewHistoryAction()
        val actualNavigationEvent = deferredNavigationEvent.await()

        // Then
        assertEquals(expectedNavigationEvent, actualNavigationEvent)
    }

    @Test
    fun `When onOpenSourceNoticesAction then emits OnViewOpenSourceNotices`() = runTest {
        val deferredNavigationEvent = async(start = UNDISPATCHED) {
            classUnderTest.navigationEvent.first()
        }
        val expectedNavigationEvent = OnViewOpenSourceNotices

        // When
        classUnderTest.onOpenSourceNoticesAction()
        val actualNavigationEvent = deferredNavigationEvent.await()

        // Then
        assertEquals(expectedNavigationEvent, actualNavigationEvent)
    }

    private fun viewStateConnected(ipAddress: String): HomeViewState.Connected {
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
        return givenConnectionDetails
    }

    private fun domainConnectionDetails(ipAddress: String): ConnectionDetailsDomainModel.Connected {
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
        return domainConnectionDetails
    }
}
