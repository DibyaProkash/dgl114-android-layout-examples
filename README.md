# Android Layouts - Step-by-Step Tutorial
## Complete Guide to Building an Android Layouts Learning App with Jetpack Compose
This tutorial will guide you through building a comprehensive Android app that demonstrates layout concepts, state management, and lists in Jetpack Compose. By the end, you'll understand how modern Android UI development works.

## Learning Objectives
By completing this tutorial, you will learn:
- How to create and structure an Android Compose project
- Understanding density-independent pixels (dp) and why they matter
- Building layouts with Box, Column, and Row
- Managing UI state reactively
- Creating efficient scrollable lists with LazyColumn
- Navigation between multiple screens
- Best practices for modern Android UI development


## Prerequisites
- Android Studio (latest version recommended)
- Basic understanding of Kotlin programming
- JDK 8 or higher installed

## Part 1: Project Setup
#### Step 1.1: Create a New Android Project

1. Open Android Studio
2. Click "New Project"
3. Select "Empty Activity" (under Phone and Tablet)
4. Click Next
5. Configure your project:
    - Name: LayoutDemo
    - Package name: com.example.layoutdemo
    - Save location: Choose your preferred location
    - Language: Kotlin
    - Minimum SDK: API 24 (Android 7.0)
    - Build configuration language: Kotlin DSL (build.gradle.kts)
6. Click Finish and wait for Gradle to sync

#### What just happened?
- Android Studio created a new project with all necessary files
- Gradle (the build system) downloaded required dependencies
- A basic Activity (screen) was created for you

### Step 1.2: Update Dependencies
Navigate to `app/build.gradle.kts` and replace its contents with:

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.layoutdemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.layoutdemo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    
    kotlinOptions {
        jvmTarget = "1.8"
    }
    
    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    
    // Compose BOM (Bill of Materials) - manages Compose versions
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    
    // Navigation for moving between screens
    implementation("androidx.navigation:navigation-compose:2.7.7")
    
    // Material Icons
    implementation("androidx.compose.material:material-icons-extended")
    
    // Debug tools
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
```
#### Understanding the Dependencies:

- `core-ktx`: Kotlin extensions for Android APIs
- `activity-compose`: Enables Compose in Activities
- `compose-bom`: Manages all Compose library versions together (BOM = Bill of Materials)
- `material3`: Material Design 3 components (buttons, cards, etc.)
- `navigation-compose`: Navigation between different screens
- `material-icons-extended`: Access to Material Design icons

Click "Sync Now" when prompted (top right of the editor).

### Step 1.3: Create the Theme Package
Themes define the visual style of your app (colors, typography, shapes). **[_if the file already exists then not need to create it_]**
1. Right-click on com.example.layoutdemo package
2. Select New → Package
3. Name it: ui.theme
4. Click OK

#### Why create a theme?

- Provides consistent styling across your entire app
- Enables dark mode support
- Makes it easy to change colors/fonts globally

### Step 1.4: Create Color.kt
Right-click on the theme package → New → Kotlin Class/File → Select File → Name: Color
Paste this code:

```kotlin
package com.example.layoutdemo.ui.theme

import androidx.compose.ui.graphics.Color

// Light theme colors
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

// Dark theme colors
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
```
