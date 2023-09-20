package com.mitteloupe.whoami.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameMatching
import com.lemonappdev.konsist.api.verify.assert
import org.junit.Test

class ArchitectureDataTest {
    @Test
    fun `Repositories reside in a repository package under data`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameMatching("^.*Repository(Impl)?$".toRegex())
            .assert { it.resideInPackage("..data..repository") }
    }
}
