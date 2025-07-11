package com.mitteloupe.whoami.datasource.history.datasource

import android.content.SharedPreferences
import com.mitteloupe.whoami.coroutine.currentValue
import com.mitteloupe.whoami.datasource.history.mapper.NewIpAddressRecordLocalMapper
import com.mitteloupe.whoami.datasource.history.mapper.SavedIpAddressRecordDataMapper
import com.mitteloupe.whoami.datasource.history.model.HistoryRecordDeletionIdentifierDataModel
import com.mitteloupe.whoami.datasource.history.model.NewIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordLocalModel
import com.mitteloupe.whoami.datasource.json.JsonDecoder
import com.mitteloupe.whoami.datasource.json.JsonEncoder
import com.mitteloupe.whoami.datasource.local.LocalStoreKey.KEY_HISTORY_RECORDS
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsEmptyCollection.empty
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

private typealias SavedIpAddressHistoryRecordLocalMap =
    Map<String, SavedIpAddressHistoryRecordLocalModel>

@RunWith(MockitoJUnitRunner::class)
class IpAddressHistoryDataSourceImplTest {
    private lateinit var classUnderTest: IpAddressHistoryDataSourceImpl

    @Mock
    private lateinit var newIpAddressRecordLocalMapper: NewIpAddressRecordLocalMapper

    @Mock
    private lateinit var savedIpAddressRecordDataMapper: SavedIpAddressRecordDataMapper

    @Mock
    private lateinit var preferencesEditor: SharedPreferences.Editor

    private lateinit var sharedPreferences: SharedPreferences

    @Mock
    private lateinit var jsonEncoder: JsonEncoder<SavedIpAddressHistoryRecordLocalMap>

    @Mock
    private lateinit var jsonDecoder: JsonDecoder<SavedIpAddressHistoryRecordLocalMap>

    @Before
    fun setUp() {
        sharedPreferences = mock {
            on { edit() } doReturn preferencesEditor
        }

        classUnderTest = IpAddressHistoryDataSourceImpl(
            newIpAddressRecordLocalMapper,
            savedIpAddressRecordDataMapper,
            sharedPreferences,
            jsonEncoder,
            jsonDecoder
        )
    }

    @Test
    fun `Given no records saved when allRecords then returns an empty collection`() = runTest {
        // When
        val actualRecords = classUnderTest.allRecords().currentValue()

        // Then
        assertThat(actualRecords, empty())
    }

    @Test
    fun `Given record was saved when allRecords then returns saved record`() = runTest {
        // Given
        val ipAddress = "0.0.0.0"
        val givenRecord = NewIpAddressHistoryRecordDataModel(
            ipAddress = ipAddress,
            city = null,
            region = null,
            countryCode = null,
            geolocation = null,
            internetServiceProviderName = null,
            postCode = null,
            timeZone = null
        )
        val localRecord = localSavedIpAddressHistoryRecord(ipAddress = ipAddress)
        given(newIpAddressRecordLocalMapper.toLocal(givenRecord))
            .willReturn(localRecord)
        val expectedRecord = dataSavedIpAddressHistoryRecord(ipAddress)
        givenMappedToData(localRecord, expectedRecord)
        classUnderTest.save(givenRecord)
        val expectedCollectionSize = 1

        // When
        val actualRecords = classUnderTest.allRecords().currentValue()

        // Then
        assertEquals(expectedCollectionSize, actualRecords.size)
        assertEquals(expectedRecord, actualRecords.first())
    }

    @Test
    fun `Given records,delete identifier when delete then emits remaining records`() = runTest {
        // Given
        val ipAddressToDelete = "1.1.1.1"
        val recordsString = "{JSON}"
        given(sharedPreferences.getString(eq(KEY_HISTORY_RECORDS), anyOrNull()))
            .willReturn(recordsString)
        val localSavedIpAddressHistoryRecord1 =
            localSavedIpAddressHistoryRecord(ipAddress = "0.0.0.0")
        val localSavedIpAddressHistoryRecordToDelete =
            localSavedIpAddressHistoryRecord(ipAddress = ipAddressToDelete)
        val localSavedIpAddressHistoryRecord3 =
            localSavedIpAddressHistoryRecord(ipAddress = "2.2.2.2")
        val decodedRecords = mapOf(
            localSavedIpAddressHistoryRecord1.ipAddress to localSavedIpAddressHistoryRecord1,
            ipAddressToDelete to localSavedIpAddressHistoryRecordToDelete,
            localSavedIpAddressHistoryRecord3.ipAddress to localSavedIpAddressHistoryRecord3
        )
        given(jsonDecoder.decode(recordsString)).willReturn(decodedRecords)
        val deletionIdentifier = HistoryRecordDeletionIdentifierDataModel(ipAddressToDelete)
        val dataSavedIpAddressHistoryRecord1 = dataSavedIpAddressHistoryRecord("0.0.0.0")
        givenMappedToData(localSavedIpAddressHistoryRecord1, dataSavedIpAddressHistoryRecord1)
        val dataSavedIpAddressHistoryRecord3 = dataSavedIpAddressHistoryRecord("2.2.2.2")
        givenMappedToData(localSavedIpAddressHistoryRecord3, dataSavedIpAddressHistoryRecord3)

        // When
        classUnderTest.delete(deletionIdentifier)
        val actualRecords = classUnderTest.allRecords().currentValue()

        // Then
        assertThat(
            actualRecords,
            hasItems(dataSavedIpAddressHistoryRecord1, dataSavedIpAddressHistoryRecord3)
        )
        assertEquals(2, actualRecords.size)
    }

    @Test
    fun `Given records,delete identifier when delete then deletes identified record`() = runTest {
        // Given
        val ipAddressToDelete = "1.1.1.1"
        val recordsString = "{JSON}"
        given(sharedPreferences.getString(eq(KEY_HISTORY_RECORDS), anyOrNull()))
            .willReturn(recordsString)
        val localSavedIpAddressHistoryRecord1 =
            localSavedIpAddressHistoryRecord(ipAddress = "0.0.0.0")
        val localSavedIpAddressHistoryRecordToDelete =
            localSavedIpAddressHistoryRecord(ipAddress = ipAddressToDelete)
        val localSavedIpAddressHistoryRecord3 =
            localSavedIpAddressHistoryRecord(ipAddress = "2.2.2.2")
        val decodedRecords = mapOf(
            localSavedIpAddressHistoryRecord1.ipAddress to localSavedIpAddressHistoryRecord1,
            ipAddressToDelete to localSavedIpAddressHistoryRecordToDelete,
            localSavedIpAddressHistoryRecord3.ipAddress to localSavedIpAddressHistoryRecord3
        )
        given(jsonDecoder.decode(recordsString)).willReturn(decodedRecords)
        val deletionIdentifier = HistoryRecordDeletionIdentifierDataModel(ipAddressToDelete)
        val dataSavedIpAddressHistoryRecord1 = dataSavedIpAddressHistoryRecord("0.0.0.0")
        givenMappedToData(localSavedIpAddressHistoryRecord1, dataSavedIpAddressHistoryRecord1)
        val dataSavedIpAddressHistoryRecord3 = dataSavedIpAddressHistoryRecord("2.2.2.2")
        givenMappedToData(localSavedIpAddressHistoryRecord3, dataSavedIpAddressHistoryRecord3)

        val recordsAfterDeletion = mapOf(
            localSavedIpAddressHistoryRecord1.ipAddress to localSavedIpAddressHistoryRecord1,
            localSavedIpAddressHistoryRecord3.ipAddress to localSavedIpAddressHistoryRecord3
        )
        val recordsAfterDeletionString = "{JSON2}"
        given(jsonEncoder.encode(recordsAfterDeletion))
            .willReturn(recordsAfterDeletionString)

        // When
        classUnderTest.delete(deletionIdentifier)

        // Then
        verify(preferencesEditor).putString(KEY_HISTORY_RECORDS, recordsAfterDeletionString)
    }

    private fun givenMappedToData(
        localRecord: SavedIpAddressHistoryRecordLocalModel,
        expectedRecord: SavedIpAddressHistoryRecordDataModel
    ) {
        given(savedIpAddressRecordDataMapper.toData(localRecord))
            .willReturn(expectedRecord)
    }

    private fun localSavedIpAddressHistoryRecord(ipAddress: String) =
        SavedIpAddressHistoryRecordLocalModel(
            ipAddress = ipAddress,
            city = null,
            region = null,
            countryCode = null,
            geolocation = null,
            internetServiceProviderName = null,
            postCode = null,
            timeZone = null,
            savedAtTimestampMilliseconds = 123L
        )

    private fun dataSavedIpAddressHistoryRecord(ipAddress: String) =
        SavedIpAddressHistoryRecordDataModel(
            ipAddress = ipAddress,
            city = null,
            region = null,
            countryCode = null,
            geolocation = null,
            internetServiceProviderName = null,
            postCode = null,
            timeZone = null,
            savedAtTimestampMilliseconds = 123L
        )
}
