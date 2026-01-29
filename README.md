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
