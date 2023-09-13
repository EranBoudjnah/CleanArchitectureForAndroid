package com.mitteloupe.whoami.datasource.connection.model

sealed interface ConnectionStateDataModel {
    data object Unset : ConnectionStateDataModel
    data object Connected : ConnectionStateDataModel
    data object Disconnected : ConnectionStateDataModel
}
