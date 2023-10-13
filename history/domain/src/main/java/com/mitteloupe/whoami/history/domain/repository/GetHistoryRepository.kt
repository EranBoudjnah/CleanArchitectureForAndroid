package com.mitteloupe.whoami.history.domain.repository

import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel
import kotlinx.coroutines.flow.Flow

interface GetHistoryRepository {
    fun history(): Flow<Collection<SavedIpAddressRecordDomainModel>>
}
