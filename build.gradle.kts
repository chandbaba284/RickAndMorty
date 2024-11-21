import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.js.translate.context.Namer.kotlin

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.detekt.plugin) apply true
}

detekt {
    toolVersion = "1.23.7"
    config.setFrom(file("detekt.yml"))
    buildUponDefaultConfig = true
    val input = projectDir
    val exclude = listOf("**/build/**", "**/resources/**")
    source.setFrom(
        fileTree(input) {
            exclude(exclude)
            include("src/main/kotlin", "src/main/java")  // Explicitly include the source directories

        }
    )
    parallel = true // Run Detekt in parallel
}

dependencies {
    detektPlugins(libs.detekt)
    detektPlugins(libs.detekt.format)
}

val detektFormat by tasks.registering(Detekt::class) {
    description = "Formats whole project."
    parallel = true
    disableDefaultRuleSets = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(analysisDir)
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
}

tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}

val analysisDir = file(projectDir)
val configFile = file("$rootDir")
val kotlinFiles = "**/*.kt"
val kotlinScriptFiles = "**/*.kts"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"
