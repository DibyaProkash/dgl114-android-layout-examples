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
1. Right-click on `com.example.layoutdemo package`
2. Select New → Package
3. Name it: `ui.theme`
4. Click `OK`

#### Why create a theme?

- Provides consistent styling across your entire app
- Enables dark mode support
- Makes it easy to change colors/fonts globally

### Step 1.4: Create Color.kt
Right-click on the `theme` package → New → Kotlin Class/File → Select File → Name: `Color`
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
Right-click on the `theme` package → New → Kotlin Class/File → Select File → Name: `Type`
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
Right-click on the `theme` package → New → Kotlin Class/File → Select File → Name: `Theme`
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
#### Understanding the Theme:
#### What is a Composable function?
- Functions marked with @Composable can display UI
- They can be called from other Composable functions
- They describe what the UI should look like

#### Theme components:
- `colorScheme`: Colors for light/dark mode
- `typography`: Text styles
- `content`: The UI that will use this theme

#### Dynamic colors (Android 12+):
- Automatically generates colors from your wallpaper
- Makes apps feel more integrated with the system

#### Status bar:
- The top bar showing time, battery, etc.
- We're making it match our app's primary color

## Part 2: Demo 1 - Basic Layouts
Now we'll create our first demo showing fundamental layout concepts.

### Step 2.1: Create `BasicLayoutDemo.kt`
Right-click on `com.example.layoutdemo` → New → Kotlin Class/File → File → Name: `BasicLayoutDemo`
Paste this code:
```kotlin
package com.example.layoutdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BasicLayoutDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Demo 1: Basic Layouts & Spacing",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        MarginVsPaddingExample()
        
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        
        SizingExample()
        
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        
        SpacingExample()
    }
}

@Composable
fun MarginVsPaddingExample() {
    Column {
        Text(
            text = "Margin vs Padding",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // View with margin only
        Box(
            modifier = Modifier
                .padding(16.dp) // MARGIN: space outside the view
                .background(Color(0xFFBBDEFB))
                .size(150.dp, 60.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("View with Margin", color = Color.Black)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // View with margin AND padding
        Box(
            modifier = Modifier
                .padding(16.dp) // MARGIN: space outside
                .background(Color(0xFF90CAF9))
                .size(150.dp, 60.dp)
                .padding(16.dp), // PADDING: space inside
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Margin + Padding",
                color = Color.White,
                modifier = Modifier.background(Color(0xFF1976D2))
            )
        }
    }
}

@Composable
fun SizingExample() {
    Column {
        Text(
            text = "Sizing Approaches",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Fixed size (exact dimensions)
        Box(
            modifier = Modifier
                .size(100.dp, 48.dp)
                .background(Color(0xFFFFA726)),
            contentAlignment = Alignment.Center
        ) {
            Text("Fixed: 100dp x 48dp", fontSize = 12.sp)
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Wrap content (size based on content)
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(Color(0xFF66BB6A))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Wrap Content", color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Fill max width (stretch to fill available space)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFFEF5350)),
            contentAlignment = Alignment.Center
        ) {
            Text("Fill Max Width", color = Color.White)
        }
    }
}

@Composable
fun SpacingExample() {
    Column {
        Text(
            text = "Spacing Between Items",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f) // Each takes equal space
                        .height(60.dp)
                        .background(Color(0xFF9575CD)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Item ${index + 1}", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BasicLayoutDemoPreview() {
    MaterialTheme {
        BasicLayoutDemo()
    }
}
```

#### Deep Dive: Understanding the Code
1. Column Layout:
```kotlin
Column(
    modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF5F5F5))
        .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
)
```
- `Column`: Arranges children vertically (like a vertical list)
- `Modifier`: Chains properties like styling in CSS
- `.fillMaxSize()`: Takes up entire screen
- `.background()`: Sets background color
- `.padding(16.dp)`: Adds space around all edges
- `verticalArrangement`: Controls spacing between children

2. Box Layout:
```kotlin
Box(
    modifier = Modifier.size(150.dp, 60.dp),
    contentAlignment = Alignment.Center
)
```
- `Box`: Stacks children on top of each other (like CSS position: relative)
- `size(width, height)`: Sets exact dimensions
- `contentAlignment`: Centers content inside

3. Row Layout:
```kotlin
Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
)
```
- `Row`: Arranges children horizontally
- `horizontalArrangement`: Controls spacing between children

4. Weight Modifier:
```kotlin
modifier = Modifier.weight(1f)
```
- `weight`: Proportional sizing
- `weight(1f)` means "take 1 part of available space"
- If 3 items have `weight(1f)`, each gets 1/3 of space
- If weights are 1f, 2f, 3f, they get 1/6, 2/6, 3/6 of space

5. Density-Independent Pixels (dp):
```kotlin
.padding(16.dp)
.size(100.dp, 48.dp)
```
- `dp`: Adjusts for different screen densities
- 16dp looks roughly the same size on all devices
- Without dp, UI would look huge on low-res screens and tiny on high-res screens

6. Scalable Pixels (sp):
```kotlin
fontSize = 12.sp
```
- `sp`: Like dp but respects user's font size settings
- Always use sp for text sizes (accessibility)

7. Preview Annotation:
```kotlin
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BasicLayoutDemoPreview()
```

- `@Preview`: Shows preview in Android Studio
- No need to run the app to see how it looks
- `showSystemUi`: Shows status bar in preview

### Student Exercise:
Before moving on, try these modifications:
- Change the padding from 16.dp to 32.dp and see the difference
- Add another Box with a different color
- Change `weight(1f)` to different values like `weight(2f)` in one box
- Try changing `Arrangement.spacedBy(8.dp)` to `Arrangement.SpaceEvenly`

## Part 3: Demo 2 - Layout Positioning
### Step 3.1: Create `ConstraintLayoutDemo.kt`
Right-click on `com.example.layoutdemo` → New → Kotlin Class/File → File → Name: `ConstraintLayoutDemo`
Paste this code:
```kotlin
package com.example.layoutdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ConstraintLayoutDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Text(
            text = "Demo 2: Layout Positioning",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        
        CenteringExample()
        
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        
        ProfileLayoutExample()
        
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        
        ButtonRowExample()
    }
}

@Composable
fun CenteringExample() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "1. Centering with Box",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Box allows you to center content easily",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Box with center alignment
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.size(120.dp, 60.dp),
                color = Color(0xFF42A5F5),
                shape = MaterialTheme.shapes.medium
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("Centered", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ProfileLayoutExample() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "2. Profile Layout with Row & Column",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Profile image and info in a Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Profile image placeholder
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(
                                Color(0xFF9C27B0),
                                shape = MaterialTheme.shapes.medium
                            )
                    )
                    
                    // Name and bio in a Column
                    Column(
                        modifier = Modifier
                            .weight(1f) // Takes remaining space
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = "John Doe",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Android Developer | Compose Enthusiast",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Buttons row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Follow")
                    }
                    
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Message")
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonRowExample() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "3. Evenly Spaced Buttons (Weight)",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Weight modifier distributes space equally",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Action 1")
                }
                
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Action 2")
                }
                
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Action 3")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConstraintLayoutDemoPreview() {
    MaterialTheme {
        ConstraintLayoutDemo()
    }
}
```
#### Understanding Layout Positioning:
1. Centering with Box:
```kotlin
Box(
    modifier = Modifier.fillMaxWidth().height(200.dp),
    contentAlignment = Alignment.Center
)
```
- Box is perfect for overlapping or centering content
- `contentAlignment = Alignment.Center` centers everything inside

Other Alignment options:

- `Alignment.TopStart` - top left
- `Alignment.TopCenter` - top center
- `Alignment.TopEnd` - top right
- `Alignment.CenterStart` - middle left
- `Alignment.Center` - exact center
- `Alignment.CenterEnd` - middle right
- `Alignment.BottomStart` - bottom left
- `Alignment.BottomCenter` - bottom center
- `Alignment.BottomEnd` - bottom right

2. Complex Layouts with Row and Column:
```kotlin
Row {
    Box(/* image */)
    Column(modifier = Modifier.weight(1f)) {
        Text(/* name */)
        Text(/* bio */)
    }
}
```

- **Row** for horizontal layout (image next to text)
- **Column** for vertical stack (name above bio)
- `weight(1f)` makes column take remaining space after image

3. Surface vs Box vs Card:
- **Box**: Basic container, no styling
- **Surface**: Box with Material Design styling (elevation, color)
- **Card**: Surface with rounded corners and shadow

### Student Exercise:
Try creating a custom profile layout:

- Add another piece of information (like location or followers count)
- Change the button arrangement to vertical (use Column instead of Row)
- Add more buttons with different weights

## Part 4: Demo 3 - Advanced Layouts
### Step 4.1: Create `GuidelinesDemo.kt`
Right-click on `com.example.layoutdemo` → New → Kotlin Class/File → File → Name: `GuidelinesDemo`
Paste this code:
```kotlin
package com.example.layoutdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GuidelinesDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Demo 3: Advanced Layouts",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Text(
            text = "Using weight and arrangement for flexible layouts",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        
        GridLayoutExample()
        
        Spacer(modifier = Modifier.height(8.dp))
        
        ProportionalLayoutExample()
    }
}

@Composable
fun GridLayoutExample() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "1. Grid Layout (3x3 using weight)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // 3x3 Grid using nested Rows and Columns
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                // Row 1
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    GridBox("1", Color(0xFFE57373), Modifier.weight(1f))
                    GridBox("2", Color(0xFFFFB74D), Modifier.weight(1f))
                    GridBox("3", Color(0xFFFFD54F), Modifier.weight(1f))
                }
                
                // Row 2
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    GridBox("4", Color(0xFF81C784), Modifier.weight(1f))
                    GridBox("5", Color(0xFF4DD0E1), Modifier.weight(1f))
                    GridBox("6", Color(0xFF64B5F6), Modifier.weight(1f))
                }
                
                // Row 3
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    GridBox("7", Color(0xFF9575CD), Modifier.weight(1f))
                    GridBox("8", Color(0xFFBA68C8), Modifier.weight(1f))
                    GridBox("9", Color(0xFFF06292), Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ProportionalLayoutExample() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "2. Proportional Sizing (Different Weights)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Different weight distribution
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // This takes 1/6 of the space
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color(0xFF42A5F5)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("1", color = Color.White)
                }
                
                // This takes 2/6 of the space
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                        .background(Color(0xFF66BB6A)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("2", color = Color.White)
                }
                
                // This takes 3/6 of the space
                Box(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxHeight()
                        .background(Color(0xFFEF5350)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("3", color = Color.White)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Dashboard-style layout
            Text(
                text = "Dashboard Layout Example:",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Top row - two equal cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DashboardCard(
                        title = "Users",
                        value = "1,234",
                        color = Color(0xFF42A5F5),
                        modifier = Modifier.weight(1f)
                    )
                    DashboardCard(
                        title = "Revenue",
                        value = "$5.6K",
                        color = Color(0xFF66BB6A),
                        modifier = Modifier.weight(1f)
                    )
                }
                
                // Bottom row - one wide card
                DashboardCard(
                    title = "Activity Today",
                    value = "89%",
                    color = Color(0xFFFFB74D),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun GridBox(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(color)
            .border(1.dp, Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun DashboardCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GuidelinesDemoPreview() {
    MaterialTheme {
        GuidelinesDemo()
    }
}
```
#### Understanding Advanced Layouts:
1. Creating Grids with Nested Rows and Columns:
```kotlin
Column {
    Row { Box(); Box(); Box() }  // First row of 3 items
    Row { Box(); Box(); Box() }  // Second row of 3 items
    Row { Box(); Box(); Box() }  // Third row of 3 items
}
```
- Outer Column stacks rows vertically
- Each Row contains 3 boxes horizontally
- Each box has `weight(1f)` to take equal space

2. Proportional Sizing with Different Weights:
```kotlin
Row {
    Box(modifier = Modifier.weight(1f))  // Gets 1/6 of width
    Box(modifier = Modifier.weight(2f))  // Gets 2/6 of width
    Box(modifier = Modifier.weight(3f))  // Gets 3/6 of width
}
```
- Total weight = 1 + 2 + 3 = 6
- First box: 1/6 of available space
- Second box: 2/6 (twice the first)
- Third box: 3/6 (half the available space)

3. Reusable Components:
```kotlin
@Composable
fun GridBox(text: String, color: Color, modifier: Modifier = Modifier)
```
- Creating reusable components makes code cleaner
- `modifier: Modifier = Modifier` allows parent to customize appearance
- Follow this pattern for all reusable UI elements

### Student Exercise:
- Try creating a 4x4 grid instead of 3x3
- Create a dashboard with 3 cards in the top row with weights 1f, 2f, 1f
- Build a custom card component for a different use case

## Part 5: Demo 4 - State Management
State management is how we make UI reactive to data changes.

### Step 5.1: Create `StateManagementDemo.kt`
Right-click on `com.example.layoutdemo` → New → Kotlin Class/File → File → Name: `StateManagementDemo`
Paste this code:
```kotlin
package com.example.layoutdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Data class - like a model in MVC
data class User(
    var name: String,
    var email: String,
    var age: Int
)

@Composable
fun StateManagementDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "Demo 4: State Management",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "This is Compose's equivalent to Data Binding",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        SimpleStateExample()
        
        Spacer(modifier = Modifier.height(24.dp))
        Divider()
        Spacer(modifier = Modifier.height(24.dp))
        
        ObjectStateExample()
        
        Spacer(modifier = Modifier.height(24.dp))
        Divider()
        Spacer(modifier = Modifier.height(24.dp))
        
        StatefulCounter()
    }
}

@Composable
fun SimpleStateExample() {
    // remember: survives recomposition
    // mutableStateOf: creates observable state
    var counter by remember { mutableStateOf(0) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "1. Simple State Example",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // This text automatically updates when counter changes
            Text(
                text = "Counter: $counter",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { counter-- }) {
                    Text("-")
                }
                
                Button(onClick = { counter++ }) {
                    Text("+")
                }
                
                Button(onClick = { counter = 0 }) {
                    Text("Reset")
                }
            }
            
            Text(
                text = "When counter changes, UI automatically updates (recomposes)",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun ObjectStateExample() {
    // State with a complex object
    var user by remember {
        mutableStateOf(User(name = "John Doe", email = "john@example.com", age = 25))
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "2. Object State (Data Binding Equivalent)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Display current user data
            UserDisplayCard(user)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Form to edit user data
            OutlinedTextField(
                value = user.name,
                onValueChange = { user = user.copy(name = it) },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = user.email,
                onValueChange = { user = user.copy(email = it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Age: ${user.age}")
                
                Row {
                    IconButton(onClick = { user = user.copy(age = user.age - 1) }) {
                        Text("-")
                    }
                    IconButton(onClick = { user = user.copy(age = user.age + 1) }) {
                        Text("+")
                    }
                }
            }
            
            Text(
                text = "Notice how changing any field updates the display above instantly",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun UserDisplayCard(user: User) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Current User Info:",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text("Name: ${user.name}", style = MaterialTheme.typography.bodyLarge)
            Text("Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
            Text("Age: ${user.age}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// State Hoisting Pattern (Best Practice)
@Composable
fun StatefulCounter() {
    var count by remember { mutableStateOf(0) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "3. State Hoisting Pattern",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Text(
                text = "This demonstrates separating stateful and stateless composables",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Stateless counter - receives state and callbacks
            StatelessCounter(
                count = count,
                onIncrement = { count++ },
                onDecrement = { count-- }
            )
        }
    }
}

@Composable
fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    // This composable has no state of its own
    // It receives everything it needs as parameters
    // This makes it reusable and testable
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$count",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Button(onClick = onDecrement) {
                Text("Decrease")
            }
            Button(onClick = onIncrement) {
                Text("Increase")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StateManagementDemoPreview() {
    MaterialTheme {
        StateManagementDemo()
    }
}
```
#### Understanding State Management:
1. What is State?
```kotlin
var counter by remember { mutableStateOf(0) }
```
- State: Data that can change over time
- `remember`: Keeps the state across recompositions
- `mutableStateOf`: Makes the value observable
- `by`: Kotlin property delegation (syntactic sugar)

Without `by`:
```kotlin
val counter = remember { mutableStateOf(0) }
counter.value++  // Need to use .value
```

With `by`:
```kotlin
var counter by remember { mutableStateOf(0) }
counter++  // Direct access, cleaner
```

2. Recomposition:
When state changes, Compose automatically:

- Detects what changed
- Re-executes affected composables
- Updates only the changed UI parts

Example:
```kotlin
Text("Counter: $counter")  // Only this Text recomposes when counter changes
```

3. Data Classes and .copy():
```kotlin
user = user.copy(name = it)
```
- Data classes automatically get a `copy()` function
- `copy()` creates a new instance with specified changes
- Why? Compose needs a new object to detect changes

This won't work:
```kotlin
user.name = it  // Modifies existing object, Compose won't detect change
```
This works:
```kotlin
user = user.copy(name = it)  // New object, Compose detects change
```

4. State Hoisting:
Moving state up to make components reusable.
**Before (Stateful - not reusable):**
```kotlin
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) { Text("$count") }
}
```

**After (Stateless - reusable):**

```kotlin
@Composable
fun Counter(count: Int, onIncrement: () -> Unit) {
    Button(onClick = onIncrement) { Text("$count") }
}
```

Benefits:

- Component can be reused with different state
- Easier to test
- Parent controls the state

### Student Exercise:

1. Add a "Multiply by 2" button to the simple counter
2. Add a "phone" field to the User object
3. Create a TodoItem component with state hoisting (completed/not completed)


## Part 6: Demo 5 - LazyColumn (Lists)
LazyColumn is Compose's equivalent to RecyclerView - it efficiently displays large lists.

### Step 6.1: Create `LazyColumnDemo.kt`
Right-click on `com.example.layoutdemo` → New → Kotlin Class/File → File → Name: `LazyColumnDemo`
Paste this code:

```kotlin
package com.example.layoutdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Data model for contacts
data class Contact(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
)

@Composable
fun LazyColumnDemo() {
    // mutableStateListOf: observable list
    val contacts = remember {
        mutableStateListOf(
            Contact(1, "Alice Johnson", "alice@email.com", "555-0101"),
            Contact(2, "Bob Smith", "bob@email.com", "555-0102"),
            Contact(3, "Carol White", "carol@email.com", "555-0103"),
            Contact(4, "David Brown", "david@email.com", "555-0104"),
            Contact(5, "Eve Davis", "eve@email.com", "555-0105"),
            Contact(6, "Frank Miller", "frank@email.com", "555-0106"),
            Contact(7, "Grace Wilson", "grace@email.com", "555-0107"),
            Contact(8, "Henry Moore", "henry@email.com", "555-0108"),
            Contact(9, "Iris Taylor", "iris@email.com", "555-0109"),
            Contact(10, "Jack Anderson", "jack@email.com", "555-0110"),
            Contact(11, "Kate Thomas", "kate@email.com", "555-0111"),
            Contact(12, "Leo Jackson", "leo@email.com", "555-0112"),
            Contact(13, "Mia Harris", "mia@email.com", "555-0113"),
            Contact(14, "Noah Martin", "noah@email.com", "555-0114"),
            Contact(15, "Olivia Garcia", "olivia@email.com", "555-0115")
        )
    }
    
    var selectedContact by remember { mutableStateOf<Contact?>(null) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Demo 5: LazyColumn",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Text(
                    text = "Compose's equivalent to RecyclerView",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
        
        // Info card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "How LazyColumn Works:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "• Only renders visible items (like RecyclerView)\n" +
                            "• Automatically recycles as you scroll\n" +
                            "• No adapter needed!\n" +
                            "• Handles large datasets efficiently",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        
        // The actual list
        ContactsList(
            contacts = contacts,
            selectedContact = selectedContact,
            onContactClick = { contact ->
                selectedContact = contact
            }
        )
    }
    
    // Show dialog when contact is selected
    selectedContact?.let { contact ->
        ContactDetailDialog(
            contact = contact,
            onDismiss = { selectedContact = null }
        )
    }
}

@Composable
fun ContactsList(
    contacts: List<Contact>,
    selectedContact: Contact?,
    onContactClick: (Contact) -> Unit
) {
    // LazyColumn only composes visible items
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Header item
        item {
            Text(
                text = "Contacts (${contacts.size})",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        // List items - like RecyclerView's onBindViewHolder
        items(
            items = contacts,
            key = { contact -> contact.id }  // Like stable IDs in RecyclerView
        ) { contact ->
            ContactItem(
                contact = contact,
                isSelected = contact == selectedContact,
                onClick = { onContactClick(contact) }
            )
        }
        
        // Footer item
        item {
            Text(
                text = "End of contacts",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun ContactItem(
    contact: Contact,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // This is like the ViewHolder + item layout in RecyclerView
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Contact info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = contact.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ContactDetailDialog(
    contact: Contact,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Contact Details") },
        text = {
            Column {
                Text("Name: ${contact.name}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Email: ${contact.email}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Phone: ${contact.phone}", style = MaterialTheme.typography.bodyMedium)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LazyColumnDemoPreview() {
    MaterialTheme {
        LazyColumnDemo()
    }
}
```

#### Understanding LazyColumn:
1. LazyColumn vs Column:
```kotlin
// Regular Column - composes ALL items at once
Column {
    contacts.forEach { contact ->
        ContactItem(contact)  // All 1000 items created immediately
    }
}

// LazyColumn - only composes visible items
LazyColumn {
    items(contacts) { contact ->
        ContactItem(contact)  // Only ~10 items created at a time
    }
}
```

LazyColumn benefits:

- Better performance with large lists
- Less memory usage
- Smooth scrolling

2. Items in LazyColumn:
```kotlin
LazyColumn {
    item { /* Single item */ }
    items(listOfItems) { item -> /* For each item */ }
    items(count = 10) { index -> /* Repeat 10 times */ }
}
```
3. Key Parameter:
```kotlin
items(items = contacts, key = { contact -> contact.id })
```   

- `key`: Unique identifier for each item
- Helps Compose track items efficiently
- Improves performance during reordering/deletion
- Like `stable IDs` in RecyclerView

4. ContentPadding:
```kotlin
LazyColumn(
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
)
```
- Adds padding inside the scrollable area
- Items can scroll under this padding
- Different from padding on the LazyColumn itself

5. Clickable Modifier:
```kotlin
Card(modifier = Modifier.clickable(onClick = onClick))
```

- Makes any composable clickable
- Includes ripple effect automatically
- Can be applied to any composable

6. Nullable State:
```kotlin
var selectedContact by remember { mutableStateOf<Contact?>(null) }
selectedContact?.let { contact -> /* Show dialog */ }
```

- `Contact?`: Nullable type (can be null)
- `.let { }`: Only executes if not null
- Safe way to handle optional values

### Student Exercise:

1. Add a search bar to filter contacts
2. Add a button to add new contacts to the list
3. Implement swipe-to-delete (research `SwipeToDismiss`)


## Part 7: Demo 6 - Advanced Lists

### Step 7.1: Create `AdvancedLazyColumnDemo.kt`
Right-click on `com.example.layoutdemo` → New → Kotlin Class/File → File → Name: `AdvancedLazyColumnDemo`
Paste this code:
```kotlin
package com.example.layoutdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Data models
data class Category(val name: String, val color: Color)
data class Product(val name: String, val price: String, val category: String)

@Composable
fun AdvancedLazyColumnDemo() {
    val categories = listOf(
        Category("Electronics", Color(0xFF42A5F5)),
        Category("Clothing", Color(0xFFEF5350)),
        Category("Food", Color(0xFF66BB6A)),
        Category("Books", Color(0xFFFFCA28))
    )
    
    val products = listOf(
        Product("Laptop", "$999", "Electronics"),
        Product("Smartphone", "$699", "Electronics"),
        Product("Headphones", "$199", "Electronics"),
        Product("T-Shirt", "$29", "Clothing"),
        Product("Jeans", "$59", "Clothing"),
        Product("Sneakers", "$89", "Clothing"),
        Product("Apple", "$2", "Food"),
        Product("Bread", "$3", "Food"),
        Product("Milk", "$4", "Food"),
        Product("Novel", "$15", "Books"),
        Product("Magazine", "$8", "Books"),
        Product("Cookbook", "$25", "Books")
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Demo 6: Advanced Lists",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
        }
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // Section 1: Horizontal scrolling list
            item {
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(
                        text = "1. LazyRow (Horizontal Scroll)",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { category ->
                            CategoryChip(category)
                        }
                    }
                }
            }
            
            // Section 2: Grid layout
            item {
                Text(
                    text = "2. LazyVerticalGrid",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),  // 2 columns
                    modifier = Modifier
                        .height(400.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(products) { product ->
                        ProductCard(product)
                    }
                }
            }
            
            // Section 3: Mixed item types
            item {
                Text(
                    text = "3. Mixed Item Types",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            // Group products by category
            categories.forEach { category ->
                item {
                    CategoryHeader(category)
                }
                
                items(products.filter { it.category == category.name }) { product ->
                    ProductListItem(product)
                }
            }
        }
    }
}

@Composable
fun CategoryChip(category: Category) {
    Card(
        colors = CardDefaults.cardColors(containerColor = category.color),
        modifier = Modifier.width(120.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = product.category,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = product.price,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun CategoryHeader(category: Category) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = category.color,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun ProductListItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Text(
                text = product.price,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdvancedLazyColumnDemoPreview() {
    MaterialTheme {
        AdvancedLazyColumnDemo()
    }
}
```

#### Understanding Advanced Lists:

1. LazyRow (Horizontal Scrolling):
```kotlin
LazyRow(
    contentPadding = PaddingValues(horizontal = 16.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
) {
    items(categories) { category -> CategoryChip(category) }
}
```
- Same as LazyColumn but scrolls horizontally
- Perfect for horizontal chips, image galleries, etc.
- Uses `horizontalArrangement` instead of `verticalArrangement`

2. LazyVerticalGrid:
```kotlin
LazyVerticalGrid(
    columns = GridCells.Fixed(2),  // 2 columns
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    items(products) { product -> ProductCard(product) }
}
```

- Displays items in a grid layout
- `GridCells.Fixed(2)`: Fixed 2 columns
- `GridCells.Adaptive(minSize = 128.dp)`: As many columns as fit

3. Mixed Item Types in One List:
```kotlin
LazyColumn {
    item { Header() }
    items(list1) { Item1(it) }
    item { Divider() }
    items(list2) { Item2(it) }
}
```
- You can mix different item types
- Use `item { }` for single items
- Use `items(list) { }` for lists
- Great for complex UIs with headers, content, footers

4. Filtering Lists:
```kotlin
products.filter { it.category == category.name }
```

- `filter { }`: Creates a new list with matching items
- Lambda receives each item
- Returns true to keep, false to exclude

5. ForEach in Composables:
```kotlin
categories.forEach { category ->
    item { CategoryHeader(category) }
    items(filteredProducts) { ProductItem(it) }
}
```

- Loops through categories
- For each category, adds a header and its products
- Creates grouped sections

### Student Exercise:

1. Change the grid to 3 columns
2. Add a LazyRow with image placeholders
3. Create a shopping cart feature (add/remove products)

## Part 8: Navigation Setup
Now let's connect all our demos with navigation.

### Step 8.1: Create `MainActivity.kt`
Replace the existing `MainActivity.kt` content with:

```kotlin
package com.example.layoutdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.layoutdemo.ui.theme.LayoutDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LayoutDemoApp()
                }
            }
        }
    }
}

@Composable
fun LayoutDemoApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") {
            DemoMenuScreen(navController)
        }
        composable("basic") {
            BasicLayoutDemo()
        }
        composable("constraint") {
            ConstraintLayoutDemo()
        }
        composable("guidelines") {
            GuidelinesDemo()
        }
        composable("state") {
            StateManagementDemo()
        }
        composable("lazycolumn") {
            LazyColumnDemo()
        }
        composable("advanced") {
            AdvancedLazyColumnDemo()
        }
    }
}

@Composable
fun DemoMenuScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Android Layouts Demo",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
            text = "Select a demo to explore:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        DemoButton(
            title = "Demo 1: Basic Layouts",
            description = "Understanding DP, margins, padding, and sizing",
            onClick = { navController.navigate("basic") }
        )
        
        DemoButton(
            title = "Demo 2: Layout Positioning",
            description = "Using Box, Column, and Row for positioning",
            onClick = { navController.navigate("constraint") }
        )
        
        DemoButton(
            title = "Demo 3: Advanced Layouts",
            description = "Using weight and arrangement for flexible layouts",
            onClick = { navController.navigate("guidelines") }
        )
        
        DemoButton(
            title = "Demo 4: State Management",
            description = "Data binding equivalent in Compose",
            onClick = { navController.navigate("state") }
        )
        
        DemoButton(
            title = "Demo 5: LazyColumn Basics",
            description = "RecyclerView equivalent - scrollable lists",
            onClick = { navController.navigate("lazycolumn") }
        )
        
        DemoButton(
            title = "Demo 6: Advanced Lists",
            description = "LazyRow, grids, and mixed item types",
            onClick = { navController.navigate("advanced") }
        )
    }
}

@Composable
fun DemoButton(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DemoMenuPreview() {
    LayoutDemoTheme {
        DemoMenuScreen(rememberNavController())
    }
}
```

#### Understanding Navigation:

1. NavController:
```kotlin
val navController = rememberNavController()
```
- `NavController`: Manages navigation between screens
- `rememberNavController()`: Creates and remembers the controller
- Passed to screens that need to navigate

2. NavHost:
```kotlin
NavHost(
    navController = navController,
    startDestination = "menu"
) {
    composable("menu") { MenuScreen() }
    composable("detail") { DetailScreen() }
}
```
- `NavHost`: Container for navigation graph
- `startDestination`: First screen to show
- `composable("route") { Screen() }`: Defines each destination

3. Navigation:
```kotlin
navController.navigate("detail")
```

- Navigate to a screen by its route
- Adds to back stack automatically
- Press back to return to previous screen

4. Passing Data Between Screens:

```kotlin
// Define route with argument
composable("detail/{id}") { backStackEntry ->
    val id = backStackEntry.arguments?.getString("id")
    DetailScreen(id)
}

// Navigate with argument
navController.navigate("detail/123")
```

## Part 9: Run and Test

### Step 9.1: Sync and Build

1. Click File → Sync Project with Gradle Files
2. Wait for sync to complete
3. Click Build → Clean Project
4. Click Build → Rebuild Project

### Step 9.2: Run the App
1. Connect a device or start an emulator
2. Click the Run button (green triangle)
3. Select your device
4. Wait for the app to install and launch


### Step 9.3: Test Each Demo
Navigate through each demo and verify:
- Demo 1: Margin/padding examples display correctly
- Demo 2: Profile layout shows image, text, and buttons
- Demo 3: 3x3 grid displays with colored boxes
- Demo 4: Counter increments/decrements, user form updates display
- Demo 5: Contact list scrolls smoothly, clicking shows dialog
- Demo 6: Horizontal scroll works, grid displays products


## Summary and Key Concepts
1. Layouts:
- Column: Vertical arrangement
- Row: Horizontal arrangement
- Box: Overlapping/centering
- Modifiers: Chaining properties (`.padding()`, `.size()`, etc.)

Sizing:
- `dp`: Density-independent pixels for consistent sizing
- `sp`: Scalable pixels for text (accessibility)
- `weight()`: Proportional sizing
- `fillMaxWidth/Height/Size()`: Fill available space
- `wrapContentSize()`: Size to content

3. State Management:
- `remember { mutableStateOf() }`: Create observable state
- Recomposition: Automatic UI updates when state changes
- State hoisting: Move state up for reusability
- `by` delegation: Cleaner syntax for state access

4. Lists:
- `LazyColumn`: Vertical scrolling list (like RecyclerView)
- `LazyRow`: Horizontal scrolling list
- `LazyVerticalGrid`: Grid layout
- `items()`: Render list items efficiently
- `key`: Unique identifier for list items

5. Navigation:
- `NavController`: Manages screen transitions
- `NavHost`: Container for navigation graph
- `composable()`: Define screen destinations
- `.navigate()`: Move between screens

## Practice Exercises

### Exercise 1: Custom Profile Screen
Create a complete profile screen with:

- Profile image (use a colored Box)
- Name, bio, location, followers count
- Follow and Message buttons
- Posts section with LazyColumn

### Exercise 2: Shopping Cart
Build a shopping cart app with:

- Product list with LazyColumn
- Add to cart button
- Cart screen showing selected items
- Total price calculation with state
- Remove from cart functionality

### Exercise 3: Todo List
Create a todo list app featuring:

- Add new todos with TextField
- LazyColumn showing todos
- Check/uncheck completed todos
- Delete todos with swipe gesture
- Filter: All, Active, Completed

## Common Issues and Solutions
### Issue 1: "Unresolved reference"
Solution:
- Ensure all imports are correct
- Sync Gradle files
- Clean and rebuild project

### Issue 2: Preview not showing
Solution:
- Check `@Preview` annotation is present
- Click "Build & Refresh" in preview panel
- Ensure composable takes no parameters (or has defaults)

### Issue 3: App crashes on launch
Solution:
- Check Logcat for error messages
- Verify all package names match
- Ensure theme is properly set up

### Issue 4: Navigation not working
Solution:

- Verify route names match exactly
- Check NavController is passed correctly
- Ensure all screens are defined in NavHost
