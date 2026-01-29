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
            title = "Demo 2: ConstraintLayout",
            description = "Creating constraints, relative positioning, and chains",
            onClick = { navController.navigate("constraint") }
        )

        DemoButton(
            title = "Demo 3: Guidelines",
            description = "Using guidelines for consistent alignment",
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