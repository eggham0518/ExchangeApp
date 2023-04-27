import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = AppConfiguration.COMPILE_SDK
    namespace = AppConfiguration.APPLICATION_ID


    defaultConfig {
        applicationId = AppConfiguration.APPLICATION_ID
        minSdk = AppConfiguration.MIN_SDK
        targetSdk = AppConfiguration.TARGET_SDK
        versionCode = AppConfiguration.VERSION_CODE
        versionName = AppConfiguration.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String","API_KEY","\"${properties.getProperty("API_KEY")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtension
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    with(Deps.Compose) {
        implementation(ui)
        implementation(material)
        implementation(constraintlayout)

        implementation(icon)

        implementation(preview)
        implementation(preview_tooling)

        implementation(viewModel)
        implementation(navigation)
    }

    with(Deps.LifeCycle) {
        implementation(lifecycleRuntimeCompose)
    }

    with(Deps.AndroidX) {
        implementation(activityCompose)
        implementation(activityKtx)
    }

    with(Deps.Hilt) {
        implementation(hiltNavigatoinCompose)
        implementation(hiltAndroid)
        kapt(hiltAndroidCompiler)
    }

    with(Deps.Retrofit){
        implementation(retrofit)
        implementation(converter_gson)
        implementation(okHttpInterceptor)
    }

    implementation(Deps.Timber.timber)

    implementation ("com.chargemap.compose:numberpicker:1.0.3")
}