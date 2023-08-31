// Top-level build file where you can add configuration options common to all sub-projects/modules.
//@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.perf) apply false
    alias(libs.plugins.hilt) apply false
//    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kapt) apply false

}
//true // Needed to make the Suppress annotation work for the plugins block
//apply("${project.rootDir}/buildscripts/toml-updater-config.gradle")
