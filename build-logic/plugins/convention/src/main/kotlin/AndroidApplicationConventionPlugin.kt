import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.baltroid.apps.configureKotlinAndroid
import com.baltroid.apps.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.application.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
        }

        extensions.configure<BaseAppModuleExtension> {
            configureKotlinAndroid(this)

            defaultConfig {
                targetSdk = libs.versions.android.targetSdk.get().toInt()

                buildTypes {
                    release {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )

                        packagingOptions.resources.excludes += "DebugProbesKt.bin"
                    }

                    create("benchmark") {
                        initWith(getByName("release"))
                        signingConfig = signingConfigs.getByName("debug")
                        matchingFallbacks.add("release")
                        isDebuggable = false
                        proguardFiles("baseline-profiles-rules.pro")
                    }
                }

                packagingOptions.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}
