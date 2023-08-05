import com.baltroid.apps.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("baltroid.android.library")
            apply("baltroid.android.hilt")
        }

        dependencies {
            add("implementation", project(":core:core-ui"))

            add("implementation", libs.findLibrary("coil.kt").get())
            add("implementation", libs.findLibrary("coil.kt.compose").get())

            add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())

            add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())

        }
    }
}
