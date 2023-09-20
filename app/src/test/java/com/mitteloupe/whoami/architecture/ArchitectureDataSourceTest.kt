package com.mitteloupe.whoami.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.ext.list.withTopLevel
import com.lemonappdev.konsist.api.verify.assert
import org.junit.Test

private val dataSourceModelNameRegex = "^.*(Data|Api|Local)Model$".toRegex()

class ArchitectureDataSourceTest {
    @Test
    fun `DataSource implementations reside in a datasource package under datasource`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("DataSourceImpl")
            .assert { it.resideInPackage("..datasource..datasource") }
    }

    @Test
    fun `DataSources reside in a datasource package under datasource`() {
        Konsist.scopeFromProject()
            .interfaces()
            .withNameEndingWith("DataSource")
            .assert { it.resideInPackage("..datasource..datasource") }
    }

    @Test
    fun `DataSource models are suffixed with Data-Api-Local Model`() {
        Konsist.scopeFromProject()
            .classes()
            .withPackage("..datasource..model")
            .withTopLevel()
            .assert { it.hasNameMatching(dataSourceModelNameRegex) }
    }
}
