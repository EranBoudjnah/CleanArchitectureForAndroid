package com.mitteloupe.whoami.home.data.repository

import com.mitteloupe.whoami.datasource.connection.datasource.ConnectionDataSource
import com.mitteloupe.whoami.datasource.ipaddress.datasource.IpAddressDataSource
import com.mitteloupe.whoami.datasource.ipaddressinformation.datasource.IpAddressInformationDataSource
import com.mitteloupe.whoami.home.data.mapper.ConnectionDetailsToDomainResolver
import com.mitteloupe.whoami.home.data.mapper.ThrowableToDomainMapper
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Error
import com.mitteloupe.whoami.home.domain.repository.GetConnectionDetailsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen

private const val RETRY_DELAY_MILLISECONDS = 1000L

class ConnectionDetailsRepository(
    private val ipAddressDataSource: IpAddressDataSource,
    private val ipAddressInformationDataSource: IpAddressInformationDataSource,
    private val connectionDataSource: ConnectionDataSource,
    private val connectionDetailsToDomainResolver: ConnectionDetailsToDomainResolver,
    private val throwableToDomainMapper: ThrowableToDomainMapper
) : GetConnectionDetailsRepository {
    override fun connectionDetails(): Flow<ConnectionDetailsDomainModel> =
        connectionDataSource.observeIsConnected().map { connected ->
            connectionDetailsToDomainResolver
                .toDomain(
                    connected,
                    { ipAddressDataSource.ipAddress() },
                    ipAddressInformationDataSource::ipAddressInformation
                )
        }.retryWhen { cause, _ ->
            emit(Error(throwableToDomainMapper.toDomain(cause)))
            delay(RETRY_DELAY_MILLISECONDS)
            true
        }
}
