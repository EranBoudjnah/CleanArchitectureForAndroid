package com.mitteloupe.whoami.datasource.connection.datasource

import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel
import kotlinx.coroutines.flow.Flow

interface ConnectionDataSource {
    fun observeIsConnected(): Flow<ConnectionStateDataModel>
}
