@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    application
}

application {
    applicationName = "aoc-solutions"
    mainClass.set("aoc.solutions.MainKt")
}

dependencies {
    implementation(project(":core:aoc"))
    implementation(libs.kotlin.serializationJson)

    testImplementation(project(":core:aoc-test"))
    testImplementation(libs.kotest.runner)
    testRuntimeOnly(libs.junit.engine)
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
    outputs.upToDateWhen { false }
    testLogging.showStandardStreams = true
    systemProperties = System.getProperties().map { it.key.toString() to it.value }.toMap()
}
