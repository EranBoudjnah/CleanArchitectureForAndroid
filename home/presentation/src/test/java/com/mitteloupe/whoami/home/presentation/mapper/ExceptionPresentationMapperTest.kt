package com.mitteloupe.whoami.home.presentation.mapper

import com.mitteloupe.whoami.architecture.domain.exception.DomainException
import com.mitteloupe.whoami.architecture.domain.exception.UnknownDomainException
import com.mitteloupe.whoami.home.domain.exception.NoIpAddressDomainException
import com.mitteloupe.whoami.home.domain.exception.NoIpAddressInformationDomainException
import com.mitteloupe.whoami.home.domain.exception.ReadFailedDomainException
import com.mitteloupe.whoami.home.presentation.model.ErrorPresentationModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class ExceptionPresentationMapperTest(
    private val givenDomainException: DomainException,
    private val expectedPresentationError: ErrorPresentationModel
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0} then returns {1}")
        fun data(): Iterable<Array<*>> = setOf(
            testCase(ReadFailedDomainException(Throwable()), ErrorPresentationModel.RequestTimeout),
            testCase(NoIpAddressDomainException(), ErrorPresentationModel.NoIpAddress),
            noIpAddressInformationTestCase("1.2.3.4"),
            noIpAddressInformationTestCase("1.1.1.1"),
            testCase(UnknownDomainException(), ErrorPresentationModel.Unknown)
        )

        private fun testCase(
            domainException: DomainException,
            presentationError: ErrorPresentationModel
        ) = arrayOf(domainException, presentationError)

        private fun noIpAddressInformationTestCase(ipAddress: String) = testCase(
            NoIpAddressInformationDomainException(ipAddress),
            ErrorPresentationModel.NoIpAddressInformation(ipAddress)
        )
    }

    private lateinit var classUnderTest: ExceptionPresentationMapper

    @Before
    fun setUp() {
        classUnderTest = ExceptionPresentationMapper()
    }

    @Test
    fun `When toPresentation`() {
        // When
        val actualValue = classUnderTest.toPresentation(givenDomainException)

        // Then
        assertEquals(expectedPresentationError, actualValue)
    }
}
