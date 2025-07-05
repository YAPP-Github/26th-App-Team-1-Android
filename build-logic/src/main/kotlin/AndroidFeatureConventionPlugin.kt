import com.ninecraft.booket.convention.applyPlugins
import com.ninecraft.booket.convention.implementation
import com.ninecraft.booket.convention.api
import com.ninecraft.booket.convention.ksp
import com.ninecraft.booket.convention.project
import com.ninecraft.booket.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                "booket.android.library",
                "booket.android.library.compose",
                "booket.android.hilt",
            )

            dependencies {
                implementation(project(path = ":core:common"))
                implementation(project(path = ":core:data:api"))
                implementation(project(path = ":core:designsystem"))
                implementation(project(path = ":core:model"))
                implementation(project(path = ":core:ui"))

                implementation(libs.compose.effects)

                implementation(libs.bundles.circuit)

                api(libs.circuit.codegen.annotation)
                ksp(libs.circuit.codegen.ksp)
            }
        }
    }
}
