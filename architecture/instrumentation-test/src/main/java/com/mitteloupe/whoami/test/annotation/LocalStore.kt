package com.mitteloupe.whoami.test.annotation

@Target(AnnotationTarget.FUNCTION)
annotation class LocalStore(
    val localStoreDataIds: Array<String>
)
