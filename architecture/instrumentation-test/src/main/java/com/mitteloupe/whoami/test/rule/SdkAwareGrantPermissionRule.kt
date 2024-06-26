package com.mitteloupe.whoami.test.rule

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.VisibleForTesting
import androidx.test.internal.platform.ServiceLoaderWrapper
import androidx.test.internal.platform.content.PermissionGranter
import androidx.test.runner.permission.PermissionRequester
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@SuppressLint("RestrictedApi")
class SdkAwareGrantPermissionRule(
    private val permissionGranter: PermissionGranter,
    vararg permissions: String
) : TestRule {

    init {
        val permissionSet = satisfyPermissionDependencies(*permissions)
        permissionGranter.addPermissions(*permissionSet.toTypedArray())
    }

    @VisibleForTesting
    private fun satisfyPermissionDependencies(vararg permissions: String): Set<String> {
        val permissionsSet: MutableSet<String> = LinkedHashSet(listOf(*permissions))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (permissionsSet.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionsSet.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            permissionsSet.remove(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        return permissionsSet
    }

    override fun apply(base: Statement, description: Description): Statement =
        RequestPermissionStatement(base, permissionGranter)

    private class RequestPermissionStatement(
        private val base: Statement,
        private val permissionGranter: PermissionGranter
    ) : Statement() {

        @Throws(Throwable::class)
        override fun evaluate() {
            permissionGranter.requestPermissions()
            base.evaluate()
        }
    }

    companion object {
        fun grant(vararg permissions: String): SdkAwareGrantPermissionRule {
            val granter = ServiceLoaderWrapper.loadSingleService(PermissionGranter::class.java) {
                PermissionRequester()
            }
            return SdkAwareGrantPermissionRule(granter, *permissions)
        }
    }
}
