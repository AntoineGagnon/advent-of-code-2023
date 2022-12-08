@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    explicitApi()
}

dependencies {
    api(project(":core:aoc"))

    implementation(kotlin("stdlib"))
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotest.api)
    implementation(libs.kotest.assertions)

    testImplementation(libs.kotest.runner)
    testRuntimeOnly(libs.junit.engine)
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
