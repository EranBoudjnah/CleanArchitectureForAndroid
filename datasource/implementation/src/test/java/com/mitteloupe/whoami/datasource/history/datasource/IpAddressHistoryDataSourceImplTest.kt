package com.mitteloupe.whoami.datasource.history.datasource

import android.content.SharedPreferences
import com.mitteloupe.whoami.coroutine.currentValue
import com.mitteloupe.whoami.datasource.history.mapper.NewIpAddressRecordToLocalMapper
import com.mitteloupe.whoami.datasource.history.mapper.SavedIpAddressRecordToDataMapper
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

private typealias SavedIpAddressHistoryRecordLocalMap =
    Map<String, SavedIpAddressHistoryRecordLocalModel>

@RunWith(MockitoJUnitRunner::class)
class IpAddressHistoryDataSourceImplTest {
    private lateinit var classUnderTest: IpAddressHistoryDataSourceImpl

    @Mock
    private lateinit var newIpAddressRecordToLocalMapper: NewIpAddressRecordToLocalMapper

    @Mock
    private lateinit var savedIpAddressRecordToDataMapper: SavedIpAddressRecordToDataMapper

    private lateinit var sharedPreferences: SharedPreferences

    @Mock
    private lateinit var jsonEncoder: JsonEncoder<SavedIpAddressHistoryRecordLocalMap>

    @Mock
    private lateinit var jsonDecoder: JsonDecoder<SavedIpAddressHistoryRecordLocalMap>

    @Before
    fun setUp() {
        val preferencesEditor: SharedPreferences.Editor = mock()
        sharedPreferences = mock {
            on { edit() } doReturn preferencesEditor
        }

        classUnderTest = IpAddressHistoryDataSourceImpl(
            newIpAddressRecordToLocalMapper,
            savedIpAddressRecordToDataMapper,
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
        given(newIpAddressRecordToLocalMapper.toLocal(givenRecord))
            .willReturn(localRecord)
        val expectedRecord = dataSavedIpAddressHistoryRecord(ipAddress)
        givenLocalToDataMapped(localRecord, expectedRecord)
        classUnderTest.save(givenRecord)
        val expectedCollectionSize = 1

        // When
        val actualRecords = classUnderTest.allRecords().currentValue()

        // Then
        assertEquals(expectedCollectionSize, actualRecords.size)
        assertEquals(expectedRecord, actualRecords.first())
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
        givenLocalToDataMapped(localSavedIpAddressHistoryRecord1, dataSavedIpAddressHistoryRecord1)
        val dataSavedIpAddressHistoryRecord3 = dataSavedIpAddressHistoryRecord("2.2.2.2")
        givenLocalToDataMapped(localSavedIpAddressHistoryRecord3, dataSavedIpAddressHistoryRecord3)

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

    private fun givenLocalToDataMapped(
        localRecord: SavedIpAddressHistoryRecordLocalModel,
        expectedRecord: SavedIpAddressHistoryRecordDataModel
    ) {
        given(savedIpAddressRecordToDataMapper.toData(localRecord))
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
