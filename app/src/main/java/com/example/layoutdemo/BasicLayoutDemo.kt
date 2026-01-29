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
                .padding(16.dp) // This acts as MARGIN
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
                .padding(16.dp) // MARGIN (outside)
                .background(Color(0xFF90CAF9))
                .size(150.dp, 60.dp)
                .padding(16.dp), // PADDING (inside)
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

        // Fixed size
        Box(
            modifier = Modifier
                .size(100.dp, 48.dp)
                .background(Color(0xFFFFA726)),
            contentAlignment = Alignment.Center
        ) {
            Text("Fixed: 100dp x 48dp", fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Wrap content
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

        // Fill max width
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
                        .weight(1f)
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