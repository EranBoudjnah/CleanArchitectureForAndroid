package com.mitteloupe.whoami.home.presentation.mapper

import com.mitteloupe.whoami.architecture.domain.exception.DomainException
import com.mitteloupe.whoami.architecture.domain.exception.UnknownDomainException
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Connected
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Disconnected
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Error
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Unset
import com.mitteloupe.whoami.home.presentation.model.ErrorPresentationModel
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.MethodRule
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.given

@RunWith(Parameterized::class)
class ConnectionDetailsToPresentationMapperTest(
    private val givenConnectionDetails: ConnectionDetailsDomainModel,
    private val expectedViewState: HomeViewState,
    private val stubMapper: ExceptionPresentationMapper.() -> Unit
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0} then returns {1}")
        fun data(): Collection<Array<*>> = listOf(
            connectedTestCase(
                "1.2.3.4",
                null,
                null,
                null,
                null,
                null,
                null,
                null
            ),
            connectedTestCase(
                "1.2.3.4",
                "New York",
                "New York",
                "United States",
                "1.0,1.0",
                "Whistle",
                "A123456",
                "EST"
            ),
            testCase(Disconnected, HomeViewState.Disconnected),
            testCase(Unset, HomeViewState.Loading),
            errorTestCase(UnknownDomainException(), ErrorPresentationModel.Unknown)
        )

        private fun testCase(
            connectionDetails: ConnectionDetailsDomainModel,
            viewState: HomeViewState,
            stubMapper: ExceptionPresentationMapper.() -> Unit = {}
        ) = arrayOf(connectionDetails, viewState, stubMapper)

        private fun connectedTestCase(
            ipAddress: String,
            city: String?,
            region: String?,
            country: String?,
            geolocation: String?,
            internetServiceProviderName: String?,
            postCode: String?,
            timeZone: String?
        ) = testCase(
            Connected(
                ipAddress,
                city,
                region,
                country,
                geolocation,
                internetServiceProviderName,
                postCode,
                timeZone
            ),
            HomeViewState.Connected(
                ipAddress,
                city,
                region,
                country,
                geolocation,
                internetServiceProviderName,
                postCode,
                timeZone
            )
        )

        private fun errorTestCase(
            domainException: DomainException,
            presentationError: ErrorPresentationModel
        ) = testCase(
            Error(domainException),
            HomeViewState.Error(presentationError)
        ) {
            given { toPresentation(domainException) }
                .willReturn(presentationError)
        }
    }

    @get:Rule
    val mockitoRule: MethodRule = MockitoJUnit.rule()

    private lateinit var classUnderTest: ConnectionDetailsPresentationMapper

    @Mock
    lateinit var exceptionPresentationMapper: ExceptionPresentationMapper

    @Before
    fun setUp() {
        classUnderTest = ConnectionDetailsPresentationMapper(exceptionPresentationMapper)
    }

    @Test
    fun `When toPresentation`() {
        // Given
        exceptionPresentationMapper.stubMapper()

        // When
        val actualValue = classUnderTest.toPresentation(givenConnectionDetails)

        // Then
        assertEquals(expectedViewState, actualValue)
    }
}
