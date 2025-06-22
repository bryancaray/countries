// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}


buildscript {
    // Retrofit
    val retrofitVersion by extra("2.9.0")
    val gsonVersion by extra("2.5.0")

    // OkHttp
    val okhttpVersion by extra("4.7.2")
    val loggingInterceptor by extra("4.5.0")

    // Room
    val roomVersion by extra("2.5.1")
}