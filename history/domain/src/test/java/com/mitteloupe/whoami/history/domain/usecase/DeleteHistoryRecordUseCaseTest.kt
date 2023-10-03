package com.mitteloupe.whoami.history.domain.usecase

import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.history.domain.model.HistoryRecordDeletionDomainModel
import com.mitteloupe.whoami.history.domain.repository.DeleteHistoryRecordRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class DeleteHistoryRecordUseCaseTest {
    private lateinit var classUnderTest: DeleteHistoryRecordUseCase

    @Mock
    private lateinit var deleteHistoryRecordRepository: DeleteHistoryRecordRepository

    @Mock
    lateinit var coroutineContextProvider: CoroutineContextProvider

    @Before
    fun setUp() {
        classUnderTest =
            DeleteHistoryRecordUseCase(deleteHistoryRecordRepository, coroutineContextProvider)
    }

    @Test
    fun `Given record to delete when executeInBackground then deletes record`() {
        // Given
        val givenRecordToDelete = HistoryRecordDeletionDomainModel("192.168.0.1", 123L)

        // When
        classUnderTest.executeInBackground(givenRecordToDelete)

        // Then
        verify(deleteHistoryRecordRepository).delete(givenRecordToDelete)
    }
}
