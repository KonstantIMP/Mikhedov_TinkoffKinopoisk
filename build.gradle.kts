import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    kotlin("android") apply false

    alias(libs.plugins.detekt)
    alias(libs.plugins.versions)
    base
}

allprojects {
    group = PUBLISHING_GROUP
}

val ktlintVersion = libs.versions.ktlint.asProvider().get()
val detektFormatting = libs.detekt

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        config = rootProject.files("config/detekt.yaml")
    }

    dependencies {
        detektPlugins(detektFormatting)
    }
}

tasks {
    withType<DependencyUpdatesTask>().configureEach {
        rejectVersionIf {
            candidate.version.isStableVersion().not()
        }
    }
}

