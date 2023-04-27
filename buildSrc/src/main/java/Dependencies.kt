import Versions.activityKTXStable
import Versions.retrofitStable
import Versions.room_version


object Versions {
    const val kotlin = "1.8.20"

    const val gradlePlugin = "8.0.0"

    const val hiltStable = "2.44"
    const val hiltNavigationComposeStable = "1.0.0"

    const val activityComposeStable = "1.7.0"

    const val composeStable = "1.4.2"
    const val composeMaterial3Stable = "1.0.1"

    const val composeUIToolingPreviewStable = "1.4.1"
    const val kotlinCompilerExtension = "1.4.6"

    const val composeNavigationStable = "2.5.3"
    const val composeViewModelStable = "2.6.1"

    const val activityKTXStable = "1.7.0"

    const val lifecycleKtx = "2.6.1"
    const val lifecycleRuntimeKtx = lifecycleKtx

    const val room_version = "2.5.0"

    const val retrofitStable = "2.9.0"

    const val material = "1.4.0"
    const val accompanist = "0.28.0"

    const val lifecycle = "2.6.1"

    const val junit = "4.13.2"

    const val timber = "timber:5.0.1"
}

object Deps {
    object Gradle {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20"
        const val dependencyCheckPlugin = "com.github.ben-manes.versions"
        const val pluginVersion = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    }

    object Accompanist {
        const val systemuicontroller =
            "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"
        const val pager = "com.google.accompanist:accompanist-pager:${Versions.accompanist}"
        const val indicator =
            "com.google.accompanist:accompanist-pager-indicators:${Versions.accompanist}"
        const val navigationAnim =
            "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanist}"
        const val inset = "com.google.accompanist:accompanist-insets:${Versions.accompanist}"
        const val insetsUi = "com.google.accompanist:accompanist-insets-ui:${Versions.accompanist}"
        const val swiperefresh =
            "com.google.accompanist:accompanist-swiperefresh:${Versions.accompanist}"
        const val permissions =
            "com.google.accompanist:accompanist-permissions:${Versions.accompanist}"
    }

    object Android {
        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object AndroidX {
        const val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
        const val activityCompose =
            "androidx.activity:activity-compose:${Versions.activityComposeStable}"
        const val activityKtx = "androidx.activity:activity-ktx:${activityKTXStable}"
    }

    object LifeCycle {
        const val lifecycleRuntimeCompose = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycle}"
        const val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val lifecycleLiveDataKtx =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val lifecycleViewModelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    }

    object Hilt {
        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltStable}"
        const val hiltAndroidCompiler =
            "com.google.dagger:hilt-android-compiler:${Versions.hiltStable}"
        const val hiltNavigatoinCompose =
            "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationComposeStable}"
    }

    object Compose {

        const val ui = "androidx.compose.ui:ui:${Versions.composeStable}"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
        const val material =
            "androidx.compose.material3:material3:${Versions.composeMaterial3Stable}"

        const val icon =
            "androidx.compose.material:material-icons-extended:${Versions.composeStable}"

        const val preview =
            "androidx.compose.ui:ui-tooling:${Versions.composeUIToolingPreviewStable}"
        const val preview_tooling =
            "androidx.compose.ui:ui-tooling-preview:${Versions.composeUIToolingPreviewStable}"

        const val navigation =
            "androidx.navigation:navigation-compose:${Versions.composeNavigationStable}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModelStable}"
    }

    object Room {

        const val room_runtime = "androidx.room:room-runtime:$room_version"
        const val room_runtime_compiler = "androidx.room:room-compiler:$room_version"
        const val room_runtime_ktx = "androidx.room:room-ktx:$room_version"

    }

    object Retrofit{
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitStable"
        const val converter_gson= "com.squareup.retrofit2:converter-gson:$retrofitStable"
        const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:4.11.0"
        const val moshi = "com.squareup.retrofit2:converter-moshi:$retrofitStable"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
    }

    object Timber {
        const val timber = "com.jakewharton.timber:${Versions.timber}"
    }
}