import com.example.buildlogic.convention.getLibsCatalog
import com.example.buildlogic.convention.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidDaggerConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-kapt")
            }

            val libs = getLibsCatalog()
            dependencies {
                implementation(libs, "google-dagger")
                "kapt"(libs.findLibrary("google-dagger-compiler").get())
            }
        }
    }
}