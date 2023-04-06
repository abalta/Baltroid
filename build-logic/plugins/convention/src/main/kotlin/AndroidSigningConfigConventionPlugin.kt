import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.dsl.SigningConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

class AndroidSigningConfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val keystoreProperties = Properties()
        val keystorePropertiesFile = rootProject.file("keystore.properties")
        if (keystorePropertiesFile.exists() && keystorePropertiesFile.isFile) {
            keystorePropertiesFile.inputStream().use { input ->
                keystoreProperties.load(input)
            }
        }

        extensions.configure<BaseAppModuleExtension> {
            val debugSigningConfig = signingConfigs.getByName("debug")
            val releaseSigningConfig = createSigningConfigFromProperties(
                target = this@with,
                name = "release",
                properties = keystoreProperties
            )

            defaultConfig {
                buildTypes {
                    release {
                        signingConfig = releaseSigningConfig ?: debugSigningConfig
                    }
                }
            }
        }
    }
}

private fun BaseAppModuleExtension.createSigningConfigFromProperties(
    target: Project,
    name: String,
    properties: Properties
): SigningConfig? {
    val keystore = mapOf(
        "keyAlias" to properties.getProperty("keyAlias"),
        "keyPassword" to properties.getProperty("keyPassword"),
        "storeFile" to properties.getProperty("storeFile"),
        "storePassword" to properties.getProperty("storePassword")
    )

    if (keystore.values.any(String::isNullOrBlank)) return null

    return signingConfigs.create(name) {
        keyAlias = keystore.getValue("keyAlias")
        keyPassword = keystore.getValue("keyPassword")
        storeFile = target.file(keystore.getValue("storeFile"))
        storePassword = keystore.getValue("storePassword")
    }
}
