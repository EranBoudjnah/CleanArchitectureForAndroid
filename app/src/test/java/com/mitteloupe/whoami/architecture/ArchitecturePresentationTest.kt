package com.mitteloupe.whoami.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assert
import org.junit.Test

class ArchitecturePresentationTest {
    @Test
    fun `ViewModels reside in a presentation-viewmodel package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("ViewModel")
            .assert { it.resideInPackage("..presentation.viewmodel") }
    }

    @Test
    fun `Presentation destinations reside in a presentation-navigation package`() {
        Konsist.scopeFromProject()
            .objects()
            .withNameEndingWith("PresentationDestination")
            .assert { it.resideInPackage("..presentation.navigation") }
    }
}
