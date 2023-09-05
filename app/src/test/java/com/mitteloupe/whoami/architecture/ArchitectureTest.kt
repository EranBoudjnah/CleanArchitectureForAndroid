package com.mitteloupe.whoami.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withNameMatching
import com.lemonappdev.konsist.api.verify.assert
import org.junit.Test

class ArchitectureTest {
    @Test
    fun `Models reside in a model package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameMatching("^.*(?<!View)Model$".toRegex())
            .assert { it.resideInPackage("..model") }
    }

    @Test
    fun `Mappers reside in a mapper package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("Mapper")
            .assert { it.resideInPackage("..mapper") }
    }
}
