package com.mitteloupe.whoami.datasource.connection.datasource

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Connected
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Disconnected
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Unset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ConnectionDataSourceImpl(
    private val connectivityManager: ConnectivityManager,
    private val coroutineScope: CoroutineScope,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val networkRequestProvider: () -> NetworkRequest = {
        NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
    }
) : ConnectionDataSource {
    private val stateFlow = MutableStateFlow<ConnectionStateDataModel>(Unset)

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            emitConnectionUpdate(Connected)
        }

        override fun onLost(network: Network) {
            emitConnectionUpdate(
                if (isConnected()) {
                    Connected
                } else {
                    Disconnected
                }
            )
        }

        private fun isConnected() =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.hasCapability(NET_CAPABILITY_INTERNET) == true
    }

    override fun observeIsConnected(): Flow<ConnectionStateDataModel> {
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (_: IllegalArgumentException) {
        }
        connectivityManager.registerNetworkCallback(networkRequestProvider(), networkCallback)

        return stateFlow
    }

    private fun emitConnectionUpdate(connectionState: ConnectionStateDataModel) {
        coroutineScope.launch(coroutineContextProvider.io) {
            stateFlow.emit(connectionState)
        }
    }
}
