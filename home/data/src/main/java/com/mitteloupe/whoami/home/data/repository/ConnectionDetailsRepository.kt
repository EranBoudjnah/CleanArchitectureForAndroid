package com.mitteloupe.whoami.home.data.repository

import com.mitteloupe.whoami.datasource.connection.datasource.ConnectionDataSource
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Connected
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Disconnected
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Unset
import com.mitteloupe.whoami.datasource.ipaddress.datasource.IpAddressDataSource
import com.mitteloupe.whoami.datasource.ipaddressinformation.datasource.IpAddressInformationDataSource
import com.mitteloupe.whoami.datasource.ipaddressinformation.exception.NoIpAddressInformationDataException
import com.mitteloupe.whoami.home.data.mapper.ConnectionDetailsDomainResolver
import com.mitteloupe.whoami.home.data.mapper.ThrowableDomainMapper
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
    private val connectionDetailsDomainResolver: ConnectionDetailsDomainResolver,
    private val throwableDomainMapper: ThrowableDomainMapper
) : GetConnectionDetailsRepository {
    override fun connectionDetails(): Flow<ConnectionDetailsDomainModel> =
        connectionDataSource.observeIsConnected().map { connectionState ->
            val (optionalIpAddress, ipAddressInformation) = when (connectionState) {
                Connected -> {
                    val ipAddress = ipAddressDataSource.ipAddress()
                    ipAddress to try {
                        ipAddressInformationDataSource.ipAddressInformation(ipAddress)
                    } catch (_: NoIpAddressInformationDataException) {
                        null
                    }
                }

                Disconnected, Unset -> null to null
            }
            connectionDetailsDomainResolver
                .toDomain(connectionState, optionalIpAddress, ipAddressInformation)
        }.retryWhen { cause, _ ->
            emit(Error(throwableDomainMapper.toDomain(cause)))
            delay(RETRY_DELAY_MILLISECONDS)
            true
        }
}
