package com.qriz.app

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

fun Project.configureComposeAndroid() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.plugin.compose")
    }

    androidExtension.apply {
        buildFeatures {
            compose = true
        }
    }

    dependencies {
        val bom = platform(findLibrary("androidx-compose-bom"))
        "implementation"(bom)

        "implementation"(findLibrary("androidx-ui"))
        "implementation"(findLibrary("androidx-material3"))
        "implementation"(findLibrary("androidx-ui-graphics"))
        "implementation"(findLibrary("androidx-ui-tooling-preview"))
        "implementation"(findLibrary("androidx-ui-tooling"))
        "implementation"(findLibrary("androidx-compose-navigation"))
        "implementation"(findLibrary("kotlinx-collections-immutable"))
        "implementation"(findLibrary("androidx.lifecycle.runtime.compose"))
    }

    extensions.getByType<ComposeCompilerGradlePluginExtension>().apply {
        enableStrongSkippingMode.set(true)
    }
}
