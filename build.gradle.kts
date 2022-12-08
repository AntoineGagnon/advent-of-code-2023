import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Suppress as workaround for https://youtrack.jetbrains.com/issue/KTIJ-19369, applies to all `build.gradle.kts` files.
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

subprojects {
    tasks {
        withType<Test> {
            useJUnitPlatform()
        }

        withType<KotlinCompile>().configureEach {
            kotlinOptions.apply {
                jvmTarget = "11"
                languageVersion = "1.7"
            }
        }
    }
}
