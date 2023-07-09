package com.mitteloupe.whoami.home.domain.repository

import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Connected

interface SaveConnectionDetailsRepository {
    fun saveConnectionDetails(details: Connected)

    class FakeSaveConnectionDetailsRepository(
        private val onSave: (details: Connected) -> Unit = {}
    ) : SaveConnectionDetailsRepository {
        override fun saveConnectionDetails(details: Connected) {
            onSave(details)
        }
    }
}
