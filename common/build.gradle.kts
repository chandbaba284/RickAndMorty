plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.apollo)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.apollo.runtime)
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
}

apollo {
    service("rickAndMorty"){
        packageName.set("com.exmple.rickandmorty")
        introspection {
            endpointUrl.set("https://rickandmortyapi.com/graphql")
            schemaFile.set(file("src/main/graphql/schema.sdl"))
        }
    }
}