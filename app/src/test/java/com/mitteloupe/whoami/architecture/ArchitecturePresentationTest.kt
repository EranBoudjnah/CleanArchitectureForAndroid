package com.mitteloupe.whoami.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.functions
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withPublicOrDefaultModifier
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.ext.list.withTopLevel
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

private val publicFunctionNameRegex = "^on[A-Z0-9].*$".toRegex()

class ArchitecturePresentationTest {
    @Test
    fun `ViewModel public function names describe events`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("ViewModel")
            .functions()
            .withPublicOrDefaultModifier()
            .assertTrue { it.hasNameMatching(publicFunctionNameRegex) }
    }

    @Test
    fun `ViewModels reside in a presentation-viewmodel package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("ViewModel")
            .assertTrue { it.resideInPackage("..presentation.viewmodel") }
    }

    @Test
    fun `Presentation destinations reside in a presentation-navigation package`() {
        Konsist.scopeFromProject()
            .objects()
            .withNameEndingWith("PresentationDestination")
            .assertTrue { it.resideInPackage("..presentation.navigation") }
    }

    @Test
    fun `Presentation models are suffixed with PresentationModel`() {
        Konsist.scopeFromProject()
            .classes()
            .withPackage("..presentation..model")
            .withTopLevel()
            .assertTrue { it.hasNameEndingWith("PresentationModel") }
    }
}
