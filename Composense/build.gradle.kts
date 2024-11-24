import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.gradle.up)
}

android {
    namespace = "com.devsync.composense"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

mavenPublishing {
    coordinates("io.github.developersyndicate", "composense", "alpha-1.0.0")

    pom {
        name.set("Composense")
        description.set("Composense is a Jetpack Compose-based library for accessing and managing Android sensor data in a declarative and easy-to-use way. It simplifies the process of integrating various Android sensors, such as accelerometer, gyroscope, heart rate, and more, directly into your Compose UI. With Composense, you can seamlessly observe sensor data changes with minimal boilerplate code and automatic lifecycle management.\n" +
                "\n" +
                "The library also includes experimental APIs for advanced features like NFC tag reading, IR signal transmission, touch gesture detection, and biometric authentication (fingerprint and face recognition), enabling deeper hardware interactions for your app.")
        inceptionYear.set("2024")
        url.set("https://github.com/DeveloperSyndicate/Composense")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("DeveloperSyndicate")
                name.set("Sanjay")
                url.set("https://github.com/DeveloperSyndicate/")
            }
        }
        scm {
            url.set("https://github.com/DeveloperSyndicate/Composense/")
            connection.set("scm:git:git://github.com/DeveloperSyndicate/Composense.git")
            developerConnection.set("scm:git:ssh://git@github.com/DeveloperSyndicate/Composense.git")
        }
    }
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.ui.tooling)
    api(libs.androidx.biometric.ktx)

}