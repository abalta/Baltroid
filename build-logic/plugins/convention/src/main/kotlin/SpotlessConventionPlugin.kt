import com.baltroid.apps.libs
import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class SpotlessConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.plugins.spotless.get().pluginId)

        extensions.configure<SpotlessExtension> {
            kotlin {
                target("**/*.kt")
                targetExclude("build/**/*.kt", "**/build/**/*.kt", "config/**/*.kt")
                licenseHeaderFile(rootProject.file("config/spotless/copyright.kt"))
            }

            kotlinGradle {
                target("**/*.gradle.kts")
                targetExclude("build/**/*.gradle.kts", "**/build/**/*.gradle.kts")
                licenseHeaderFile(
                    rootProject.file("config/spotless/copyright.kt"),
                    "(plugins |pluginManagement |import |@file)"
                )
            }

            format("xml") {
                target("**/*.xml")
                targetExclude("build/**/*.xml", "**/build/**/*.xml", "config/**/*.xml")
                licenseHeaderFile(rootProject.file("config/spotless/copyright.xml"), "(<[^!?])")
            }
        }
    }
}
