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

/**
 * DEMO 6: Advanced LazyColumn Patterns
 * This demonstrates:
 * - LazyRow (horizontal scrolling)
 * - LazyVerticalGrid (grid layout)
 * - Different item types in same list
 * - Sticky headers
 * - Performance considerations
 */

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
            // Section 1: Horizontal scrolling list (LazyRow)
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
                    columns = GridCells.Fixed(2),
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