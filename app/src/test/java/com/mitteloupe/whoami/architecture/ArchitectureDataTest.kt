package com.mitteloupe.whoami.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameMatching
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class ArchitectureDataTest {
    @Test
    fun `Repositories reside in a repository package under data`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameMatching("^(?!Fake)[A-Z].*Repository(Impl)?$".toRegex())
            .assertTrue { it.resideInPackage("..data..repository") }
    }
}
