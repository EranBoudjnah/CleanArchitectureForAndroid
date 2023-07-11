package com.mitteloupe.whoami.history.domain.repository

import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel

interface GetHistoryRepository {
    fun history(): Collection<SavedIpAddressRecordDomainModel>
}
