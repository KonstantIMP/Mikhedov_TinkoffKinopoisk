import java.util.Properties

plugins {
    id("com.android.application")

    kotlin("android")
    kotlin("kapt")

    alias(libs.plugins.hilt)
    alias(libs.plugins.safeargs)
}

android {
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        namespace = "org.kimp.kinopoisk.tinkoff"

        applicationId = AppCoordinates.APP_ID
        versionCode = AppCoordinates.APP_VERSION_CODE
        versionName = AppCoordinates.APP_VERSION_NAME
        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties().apply {
            load(project.rootProject.file("local.properties").inputStream())
        }
        buildConfigField("String", "KINOPOISK_API_KEY", '"' + properties.getProperty("KINOPOISK_API_KEY") + '"')
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()

        freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.bundles.androidx)

    implementation(libs.timber)
    implementation(libs.javapoet)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.coil)
    implementation(libs.lottie)

    implementation(libs.google.material)

    implementation(libs.bundles.retrofit)
}

kapt {
    useBuildCache = true
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}
