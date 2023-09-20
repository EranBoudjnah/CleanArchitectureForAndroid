package com.mitteloupe.whoami.architecture

import androidx.fragment.app.Fragment
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withAllParentsOf
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.ext.list.withTopLevel
import com.lemonappdev.konsist.api.verify.assert
import org.junit.Test

class ArchitectureUiTest {
    @Test
    fun `Fragment names are suffixed with Fragment`() {
        Konsist.scopeFromProject()
            .classes()
            .withAllParentsOf(Fragment::class)
            .assert { it.name.endsWith("Fragment") }
    }

    @Test
    fun `Fragments reside in a view package under ui`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("Fragment")
            .assert { it.resideInPackage("..ui..view") }
    }

    @Test
    fun `UI models are suffixed with UiModel`() {
        Konsist.scopeFromProject()
            .classes()
            .withPackage("..ui..model")
            .withTopLevel()
            .assert { it.hasNameEndingWith("UiModel") }
    }
}
