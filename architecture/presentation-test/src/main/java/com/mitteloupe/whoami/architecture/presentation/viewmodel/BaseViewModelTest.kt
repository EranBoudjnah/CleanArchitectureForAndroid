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

    protected fun <REQUEST> givenFailedUseCaseExecution(
        useCase: UseCase<REQUEST, *>,
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
                    useCase = eq(useCase),
                    value = eq(input),
                    onResult = any(),
                    onException = any()
                )
        }
    }

    protected fun <REQUEST, RESULT> givenSuccessfulUseCaseExecution(
        useCase: UseCase<REQUEST, RESULT>,
        input: REQUEST,
        result: RESULT
    ) {
        willAnswer { invocationOnMock ->
            val onResult: (RESULT) -> Unit = invocationOnMock.getArgument(2)
            onResult(result)
        }.given(useCaseExecutor).execute(
            useCase = eq(useCase),
            value = eq(input),
            onResult = any(),
            onException = any()
        )
    }

    protected fun <REQUEST> givenSuccessfulNoResultUseCaseExecution(
        useCase: UseCase<REQUEST, Unit>,
        input: REQUEST
    ) {
        givenSuccessfulUseCaseExecution(useCase, input, Unit)
    }

    protected fun <RESULT> givenSuccessfulUseCaseExecution(
        useCase: UseCase<Unit, RESULT>,
        result: RESULT
    ) {
        willAnswer { invocationOnMock ->
            val onResult: (RESULT) -> Unit = invocationOnMock.getArgument(1)
            onResult(result)
        }.given(useCaseExecutor).execute(
            useCase = eq(useCase),
            onResult = any(),
            onException = any()
        )
    }

    protected fun givenSuccessfulNoArgumentNoResultUseCaseExecution(useCase: UseCase<Unit, Unit>) {
        willAnswer { invocationOnMock ->
            val onResult: (Unit) -> Unit = invocationOnMock.getArgument(2)
            onResult(Unit)
        }.given(useCaseExecutor).execute(
            useCase = eq(useCase),
            onResult = any(),
            onException = any()
        )
    }
}
