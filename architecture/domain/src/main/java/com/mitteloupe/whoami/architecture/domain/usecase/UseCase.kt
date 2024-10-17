package com.mitteloupe.whoami.architecture.domain.usecase

interface UseCase<REQUEST, RESULT> {
    fun execute(input: REQUEST, onResult: (RESULT) -> Unit)
}
