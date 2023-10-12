package com.mitteloupe.whoami.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.ext.list.withTopLevel
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class ArchitectureDomainTest {
    @Test
    fun `UseCases reside in a usecase package under domain`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..domain..usecase") }
    }

    @Test
    fun `Domain models are suffixed with DomainModel`() {
        Konsist.scopeFromProject()
            .classes()
            .withPackage("..domain..model")
            .withTopLevel()
            .assertTrue { it.hasNameEndingWith("DomainModel") }
    }

    @Test
    fun `Repository interfaces reside in a repository package under domain`() {
        Konsist.scopeFromProject()
            .interfaces()
            .withNameEndingWith("Repository")
            .assertTrue { it.resideInPackage("..domain..repository") }
    }
}
