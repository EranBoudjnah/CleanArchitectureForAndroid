package com.mitteloupe.whoami.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assert
import org.junit.Test

class ArchitectureDataSourceTest {
    @Test
    fun `DataSources reside in a datasource package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("DataSourceImpl")
            .assert { it.resideInPackage("..datasource") }
    }
}
