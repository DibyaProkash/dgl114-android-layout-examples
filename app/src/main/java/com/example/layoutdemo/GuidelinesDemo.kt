package com.example.layoutdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
 * DEMO 3: Guidelines in ConstraintLayout
 * This demonstrates:
 * - Creating vertical and horizontal guidelines
 * - Positioning guidelines with absolute values
 * - Positioning guidelines with percentage
 * - Constraining views to guidelines
 */

@Composable
fun GuidelinesDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "Demo 3: Guidelines",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Guidelines help align multiple views to a common reference line",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        GuidelinePercentageExample()

        Spacer(modifier = Modifier.height(24.dp))

        GuidelineAbsoluteExample()
    }
}

@Composable
fun GuidelinePercentageExample() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "1. Percentage Guidelines (Grid Layout)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.White)
            ) {
                // Create guidelines at 33% and 66% both horizontally and vertically
                val guideline33Horizontal = createGuidelineFromTop(0.33f)
                val guideline66Horizontal = createGuidelineFromTop(0.66f)
                val guideline33Vertical = createGuidelineFromStart(0.33f)
                val guideline66Vertical = createGuidelineFromStart(0.66f)

                // Create 9 boxes using the guidelines (3x3 grid)
                val (box1, box2, box3, box4, box5, box6, box7, box8, box9) = createRefs()

                // Row 1
                GridBox(
                    text = "1",
                    color = Color(0xFFE57373),
                    modifier = Modifier.constrainAs(box1) {
                        top.linkTo(parent.top)
                        bottom.linkTo(guideline33Horizontal)
                        start.linkTo(parent.start)
                        end.linkTo(guideline33Vertical)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                )

                GridBox(
                    text = "2",
                    color = Color(0xFFFFB74D),
                    modifier = Modifier.constrainAs(box2) {
                        top.linkTo(parent.top)
                        bottom.linkTo(guideline33Horizontal)
                        start.linkTo(guideline33Vertical)
                        end.linkTo(guideline66Vertical)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                )

                GridBox(
                    text = "3",
                    color = Color(0xFFFFD54F),
                    modifier = Modifier.constrainAs(box3) {
                        top.linkTo(parent.top)
                        bottom.linkTo(guideline33Horizontal)
                        start.linkTo(guideline66Vertical)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                )

                // Row 2
                GridBox(
                    text = "4",
                    color = Color(0xFF81C784),
                    modifier = Modifier.constrainAs(box4) {
                        top.linkTo(guideline33Horizontal)
                        bottom.linkTo(guideline66Horizontal)
                        start.linkTo(parent.start)
                        end.linkTo(guideline33Vertical)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                )

                GridBox(
                    text = "5",
                    color = Color(0xFF4DD0E1),
                    modifier = Modifier.constrainAs(box5) {
                        top.linkTo(guideline33Horizontal)
                        bottom.linkTo(guideline66Horizontal)
                        start.linkTo(guideline33Vertical)
                        end.linkTo(guideline66Vertical)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                )

                GridBox(
                    text = "6",
                    color = Color(0xFF64B5F6),
                    modifier = Modifier.constrainAs(box6) {
                        top.linkTo(guideline33Horizontal)
                        bottom.linkTo(guideline66Horizontal)
                        start.linkTo(guideline66Vertical)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                )

                // Row 3
                GridBox(
                    text = "7",
                    color = Color(0xFF9575CD),
                    modifier = Modifier.constrainAs(box7) {
                        top.linkTo(guideline66Horizontal)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(guideline33Vertical)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                )

                GridBox(
                    text = "8",
                    color = Color(0xFFBA68C8),
                    modifier = Modifier.constrainAs(box8) {
                        top.linkTo(guideline66Horizontal)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(guideline33Vertical)
                        end.linkTo(guideline66Vertical)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                )

                GridBox(
                    text = "9",
                    color = Color(0xFFF06292),
                    modifier = Modifier.constrainAs(box9) {
                        top.linkTo(guideline66Horizontal)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(guideline66Vertical)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                )
            }
        }
    }
}

@Composable
fun GuidelineAbsoluteExample() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "2. Absolute Guidelines (Consistent Margins)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.White)
            ) {
                // Create guidelines at 16dp from edges
                val startGuideline = createGuidelineFromStart(16.dp)
                val endGuideline = createGuidelineFromEnd(16.dp)
                val topGuideline = createGuidelineFromTop(16.dp)
                val bottomGuideline = createGuidelineFromBottom(16.dp)

                val (titleText, contentText, actionButton) = createRefs()

                // All views constrain to the guidelines, ensuring consistent margins
                Text(
                    text = "Card Title",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.constrainAs(titleText) {
                        top.linkTo(topGuideline)
                        start.linkTo(startGuideline)
                    }
                )

                Text(
                    text = "This content respects the 16dp margin from all edges thanks to guidelines. " +
                            "All views in this layout are constrained to the same guidelines.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.constrainAs(contentText) {
                        top.linkTo(titleText.bottom, margin = 8.dp)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    }
                )

                Button(
                    onClick = { },
                    modifier = Modifier.constrainAs(actionButton) {
                        bottom.linkTo(bottomGuideline)
                        end.linkTo(endGuideline)
                    }
                ) {
                    Text("Action")
                }
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
            .background(color)
            .border(1.dp, Color.White),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GuidelinesDemoPreview() {
    MaterialTheme {
        GuidelinesDemo()
    }
}