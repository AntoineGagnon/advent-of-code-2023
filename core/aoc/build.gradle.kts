@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(kotlin("stdlib"))
}
