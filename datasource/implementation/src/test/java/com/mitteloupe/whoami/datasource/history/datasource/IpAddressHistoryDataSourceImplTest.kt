package com.mitteloupe.whoami.datasource.history.datasource

import android.content.SharedPreferences
import com.mitteloupe.whoami.datasource.history.mapper.NewIpAddressRecordToLocalMapper
import com.mitteloupe.whoami.datasource.history.mapper.SavedIpAddressRecordToDataMapper
import com.mitteloupe.whoami.datasource.history.model.NewIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordLocalModel
import com.mitteloupe.whoami.datasource.json.JsonDecoder
import com.mitteloupe.whoami.datasource.json.JsonEncoder
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsEmptyCollection.empty
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
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
    fun `Given no records saved when allRecords then returns an empty collection`() {
        // When
        val actualRecords = classUnderTest.allRecords()

        // Then
        assertThat(actualRecords, empty())
    }

    @Test
    fun `Given record was saved when allRecords then returns saved record`() {
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
        val localRecord = SavedIpAddressHistoryRecordLocalModel(
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
        given(newIpAddressRecordToLocalMapper.toLocal(givenRecord))
            .willReturn(localRecord)
        val expectedRecord = SavedIpAddressHistoryRecordDataModel(
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
        given(savedIpAddressRecordToDataMapper.toData(localRecord))
            .willReturn(expectedRecord)
        classUnderTest.save(givenRecord)
        val expectedCollectionSize = 1

        // When
        val actualRecords = classUnderTest.allRecords()

        // Then
        assertEquals(expectedCollectionSize, actualRecords.size)
        assertEquals(expectedRecord, actualRecords.first())
    }
}
