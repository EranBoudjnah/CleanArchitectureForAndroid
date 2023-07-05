package com.mitteloupe.whoami.home.domain.repository

import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import kotlinx.coroutines.flow.Flow

interface GetConnectionDetailsRepository {
    fun connectionDetails(): Flow<ConnectionDetailsDomainModel>
}
