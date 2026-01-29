package com.example.layoutdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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

/**
 * DEMO 5: LazyColumn (RecyclerView Equivalent in Compose)
 * This demonstrates:
 * - Creating scrollable lists
 * - Efficient recycling (like RecyclerView)
 * - Different item types
 * - Item click handling
 * - List manipulation
 *
 * LazyColumn is Compose's equivalent to RecyclerView
 * It only composes visible items, recycling them as you scroll
 */

// Data models
data class Contact(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
)

@Composable
fun LazyColumnDemo() {
    // Sample data - like the adapter's dataset in RecyclerView
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

        // List explanation
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

        // The actual LazyColumn
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
    // LazyColumn is like RecyclerView
    // It only composes and lays out visible items
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
            key = { contact -> contact.id } // Like stable IDs in RecyclerView
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