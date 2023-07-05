package com.mitteloupe.whoami.home.data.mapper

import com.mitteloupe.whoami.architecture.domain.exception.DomainException
import com.mitteloupe.whoami.architecture.domain.exception.UnknownDomainException
import com.mitteloupe.whoami.datasource.ipaddress.exception.NoIpAddressDataException
import com.mitteloupe.whoami.datasource.remote.exception.RequestTimeoutDataException
import com.mitteloupe.whoami.home.domain.exception.ReadFailedDomainException
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.typeCompatibleWith
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class ThrowableToDomainMapperTest(
    private val givenThrowable: Throwable,
    private val assertion: (DomainException) -> Unit
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0}")
        fun data(): Collection<Array<*>> = listOf(
            testCase(NoIpAddressDataException()) { exception ->
                assertThat(
                    exception::class.java,
                    typeCompatibleWith(ReadFailedDomainException::class.java)
                )
            },
            testCase(RequestTimeoutDataException()) { exception ->
                assertThat(
                    exception::class.java,
                    typeCompatibleWith(ReadFailedDomainException::class.java)
                )
            },
            testCase(IllegalStateException()) { exception ->
                assertThat(
                    exception::class.java,
                    typeCompatibleWith(UnknownDomainException::class.java)
                )
            }
            /***
             *             is NoIpAddressDataException -> ReadFailedDomainException(exception)
             *             is RequestTimeoutDataException -> ReadFailedDomainException(exception)
             *             else -> UnknownDomainException(exception)
             *
             */
        )

        private fun testCase(givenThrowable: Throwable, assertion: (DomainException) -> Unit) =
            arrayOf(givenThrowable, assertion)
    }

    private lateinit var classUnderTest: ThrowableToDomainMapper

    @Before
    fun setUp() {
        classUnderTest = ThrowableToDomainMapper()
    }

    @Test
    fun `When toDomain then returns expected exception`() {
        // When
        val actualValue = classUnderTest.toDomain(givenThrowable)

        // Then
        assertion(actualValue)
    }
}
