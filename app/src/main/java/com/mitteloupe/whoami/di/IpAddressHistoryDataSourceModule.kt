package com.mitteloupe.whoami.di

import android.content.SharedPreferences
import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSourceImpl
import com.mitteloupe.whoami.datasource.history.mapper.NewIpAddressRecordToLocalMapper
import com.mitteloupe.whoami.datasource.history.mapper.SavedIpAddressRecordToDataMapper
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordLocalModel
import com.mitteloupe.whoami.datasource.json.JsonDecoder
import com.mitteloupe.whoami.datasource.json.JsonEncoder
import com.mitteloupe.whoami.datasource.json.JsonProcessor
import com.mitteloupe.whoami.time.NowProvider
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IpAddressHistoryDataSourceModule {
    @Provides
    fun providesNewIpAddressRecordToSavedMapper(
        nowProvider: NowProvider
    ) = NewIpAddressRecordToLocalMapper(nowProvider)

    @Provides
    fun providesSavedIpAddressRecordToDataMapper() = SavedIpAddressRecordToDataMapper()

    @Provides
    fun providesJsonAdapter(
        moshi: Moshi
    ): JsonAdapter<Map<String, SavedIpAddressHistoryRecordLocalModel>> = moshi.adapter(
        Types.newParameterizedType(
            Map::class.java,
            String::class.java,
            SavedIpAddressHistoryRecordLocalModel::class.java
        )
    )

    @Provides
    fun providesJsonProcessor(
        jsonAdapter: JsonAdapter<Map<String, SavedIpAddressHistoryRecordLocalModel>>
    ) = JsonProcessor(jsonAdapter)

    @Provides
    fun providesJsonEncoder(
        jsonProcessor: JsonProcessor<Map<String, SavedIpAddressHistoryRecordLocalModel>>
    ): JsonEncoder<Map<String, SavedIpAddressHistoryRecordLocalModel>> = jsonProcessor

    @Provides
    fun providesJsonDecoder(
        jsonProcessor: JsonProcessor<Map<String, SavedIpAddressHistoryRecordLocalModel>>
    ): JsonDecoder<Map<String, SavedIpAddressHistoryRecordLocalModel>> = jsonProcessor

    @Provides
    @Singleton
    fun providesIpAddressHistoryDataSource(
        newIpAddressRecordToLocalMapper: NewIpAddressRecordToLocalMapper,
        savedIpAddressRecordToDataMapper: SavedIpAddressRecordToDataMapper,
        sharedPreferences: SharedPreferences,
        jsonEncoder: JsonEncoder<Map<String, SavedIpAddressHistoryRecordLocalModel>>,
        jsonDecoder: JsonDecoder<Map<String, SavedIpAddressHistoryRecordLocalModel>>
    ): IpAddressHistoryDataSource = IpAddressHistoryDataSourceImpl(
        newIpAddressRecordToLocalMapper,
        savedIpAddressRecordToDataMapper,
        sharedPreferences,
        jsonEncoder,
        jsonDecoder
    )
}
