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

#### Understanding Colors in Compose:

- Color(0xFFD0BCFF): The format is 0xAARRGGBB
    - FF = Alpha (transparency): FF = fully opaque, 00 = fully transparent
    - D0BCFF = RGB color code (like in CSS)

- "80" and "40": Material Design naming convention
    - 80 = lighter shade (for dark mode)
    - 40 = darker shade (for light mode)

### Step 1.5: Create Type.kt
Right-click on the theme package → New → Kotlin Class/File → Select File → Name: Type
Paste this code:
```kotlin
package com.example.layoutdemo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Defines text styles used throughout the app
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,        // sp = scalable pixels (for text)
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
```

#### Understanding Typography:
- `sp (scalable pixels)`: Used for text sizes, respects user's font size settings
- `dp (density-independent pixels)`: Used for layout dimensions
- `Typography`: A collection of text styles you can reuse
- `Material Design`: Provides predefined text styles (bodyLarge, titleLarge, etc.)

#### Why separate text styles?
- Consistency: All titles look the same
- Easy updates: Change font size once, updates everywhere
- Accessibility: Users with vision impairments can increase text size

### Step 1.6: Create Theme.kt
Right-click on the theme package → New → Kotlin Class/File → Select File → Name: Theme
Paste this code:
```kotlin
package com.example.layoutdemo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Dark mode color scheme
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// Light mode color scheme
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun LayoutDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Detect system theme
    dynamicColor: Boolean = true, // Use Android 12+ dynamic colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Android 12+ supports dynamic theming (colors from wallpaper)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // Use dark theme
        darkTheme -> DarkColorScheme
        // Use light theme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

