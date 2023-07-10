package com.mitteloupe.whoami.datasource.history.datasource

import com.mitteloupe.whoami.datasource.history.mapper.NewIpAddressRecordToSavedMapper
import com.mitteloupe.whoami.datasource.history.model.NewIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsEmptyCollection.empty
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class IpAddressHistoryDataSourceImplTest {
    private lateinit var classUnderTest: IpAddressHistoryDataSourceImpl

    @Mock
    private lateinit var newIpAddressRecordToSavedMapper: NewIpAddressRecordToSavedMapper

    @Before
    fun setUp() {
        classUnderTest = IpAddressHistoryDataSourceImpl(newIpAddressRecordToSavedMapper)
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
        given(newIpAddressRecordToSavedMapper.toSaved(givenRecord))
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
