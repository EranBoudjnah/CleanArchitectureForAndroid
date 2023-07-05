package com.mitteloupe.whoami.datasource.connection.model

sealed interface ConnectionStateDataModel {
    object Unset : ConnectionStateDataModel
    object Connected : ConnectionStateDataModel
    object Disconnected : ConnectionStateDataModel
}
