import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        extensions.configure<BaseAppModuleExtension> {
            defaultConfig {
                lint {
                    warningsAsErrors = true
                    abortOnError = true
                    checkDependencies = true
                }
            }
        }
    }
}
