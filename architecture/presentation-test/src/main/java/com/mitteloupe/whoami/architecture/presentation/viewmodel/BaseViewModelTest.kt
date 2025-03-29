package com.mitteloupe.whoami.architecture.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.domain.exception.DomainException
import com.mitteloupe.whoami.architecture.domain.usecase.UseCase
import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.mockito.BDDMockito.willAnswer
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.eq

private const val ON_EXCEPTION_ARGUMENT_INDEX = 3

abstract class BaseViewModelTest<
    VIEW_STATE : Any,
    NOTIFICATION : PresentationNotification,
    VIEW_MODEL : BaseViewModel<VIEW_STATE, NOTIFICATION>
    > {
    private val testScheduler = TestCoroutineScheduler()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(testScheduler)

    protected lateinit var classUnderTest: VIEW_MODEL

    @Mock
    protected lateinit var useCaseExecutor: UseCaseExecutor

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun coroutineSetUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun coroutineTearDown() {
        Dispatchers.resetMain()
    }

    protected fun <REQUEST> UseCase<REQUEST, *>.givenFailedExecution(
        input: REQUEST,
        domainException: DomainException
    ) {
        runBlocking {
            willAnswer { invocation ->
                val onException: (DomainException) -> Unit =
                    invocation.getArgument(ON_EXCEPTION_ARGUMENT_INDEX)
                onException(domainException)
            }.given(useCaseExecutor)
                .execute(
                    useCase = eq(this@givenFailedExecution),
                    value = eq(input),
                    onResult = any(),
                    onException = any()
                )
        }
    }

    protected fun <REQUEST, RESULT> UseCase<REQUEST, RESULT>.givenSuccessfulExecution(
        input: REQUEST,
        result: RESULT
    ) {
        willAnswer { invocationOnMock ->
            val onResult: (RESULT) -> Unit = invocationOnMock.getArgument(2)
            onResult(result)
        }.given(useCaseExecutor).execute(
            useCase = eq(this@givenSuccessfulExecution),
            value = eq(input),
            onResult = any(),
            onException = any()
        )
    }

    protected fun <REQUEST> UseCase<REQUEST, Unit>.givenSuccessfulNoResultExecution(
        input: REQUEST
    ) {
        givenSuccessfulExecution(input, Unit)
    }

    protected fun <RESULT> UseCase<Unit, RESULT>.givenSuccessfulExecution(result: RESULT) {
        willAnswer { invocationOnMock ->
            val onResult: (RESULT) -> Unit = invocationOnMock.getArgument(1)
            onResult(result)
        }.given(useCaseExecutor).execute(
            useCase = eq(this@givenSuccessfulExecution),
            onResult = any(),
            onException = any()
        )
    }

    protected fun UseCase<Unit, Unit>.givenSuccessfulNoArgumentNoResultExecution() {
        willAnswer { invocationOnMock ->
            val onResult: (Unit) -> Unit = invocationOnMock.getArgument(2)
            onResult(Unit)
        }.given(useCaseExecutor).execute(
            useCase = eq(this@givenSuccessfulNoArgumentNoResultExecution),
            onResult = any(),
            onException = any()
        )
    }
}
