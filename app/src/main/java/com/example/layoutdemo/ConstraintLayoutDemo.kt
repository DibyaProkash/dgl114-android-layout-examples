package com.example.layoutdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


/**
 * DEMO 2: ConstraintLayout in Compose
 * This demonstrates:
 * - Creating constraints between views
 * - Positioning relative to parent
 * - Positioning relative to other views
 * - Creating chains
 * - Using guidelines
 */

@Composable
fun ConstraintLayoutDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Text(
            text = "Demo 2: ConstraintLayout",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        // Example 1: Simple constraints
        SimpleConstraintsExample()

        Spacer(modifier = Modifier.height(16.dp))
        Divider()

        // Example 2: Relative positioning
        RelativePositioningExample()

        Spacer(modifier = Modifier.height(16.dp))
        Divider()

        // Example 3: Chains
        ChainExample()
    }
}

@Composable
fun SimpleConstraintsExample() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "1. Center View (All sides constrained)",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White)
        ) {
            // Create references
            val (centerBox) = createRefs()

            // Center view by constraining all sides to parent
            Box(
                modifier = Modifier
                    .size(120.dp, 60.dp)
                    .background(Color(0xFF42A5F5))
                    .constrainAs(centerBox) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}

@Composable
fun RelativePositioningExample() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "2. Relative Positioning",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color.White)
                .padding(16.dp)
        ) {
            // Create references for multiple views
            val (profileImage, userName, userBio, followButton) = createRefs()

            // Profile image - top start
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFF9C27B0))
                    .constrainAs(profileImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            // Username - to the right of image, aligned to top
            Text(
                text = "John Doe",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(userName) {
                    start.linkTo(profileImage.end, margin = 16.dp)
                    top.linkTo(profileImage.top)
                }
            )

            // Bio - below username
            Text(
                text = "Android Developer | Compose Enthusiast",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.constrainAs(userBio) {
                    start.linkTo(userName.start)
                    top.linkTo(userName.bottom, margin = 4.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            // Follow button - bottom right
            Button(
                onClick = { },
                modifier = Modifier.constrainAs(followButton) {
                    top.linkTo(profileImage.bottom, margin = 16.dp)
                    end.linkTo(parent.end)
                }
            ) {
                Text("Follow")
            }
        }
    }
}

@Composable
fun ChainExample() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "3. Horizontal Chain (Spread)",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White)
                .padding(16.dp)
        ) {
            val (btn1, btn2, btn3) = createRefs()

            // Create horizontal chain
            createHorizontalChain(btn1, btn2, btn3, chainStyle = androidx.constraintlayout.compose.ChainStyle.Spread)

            Button(
                onClick = { },
                modifier = Modifier.constrainAs(btn1) {
                    centerVerticallyTo(parent)
                }
            ) {
                Text("Action 1")
            }

            Button(
                onClick = { },
                modifier = Modifier.constrainAs(btn2) {
                    centerVerticallyTo(parent)
                }
            ) {
                Text("Action 2")
            }

            Button(
                onClick = { },
                modifier = Modifier.constrainAs(btn3) {
                    centerVerticallyTo(parent)
                }
            ) {
                Text("Action 3")
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