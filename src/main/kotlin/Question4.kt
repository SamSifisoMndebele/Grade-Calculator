import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// 4.1) MyGrader: Main composable function that declares UI and state.
@Preview
@Composable
fun MyGrader() {
    // State variables for user input scores and calculated results.
    var score1 by remember { mutableStateOf("") }
    var score2 by remember { mutableStateOf("") }
    var total by remember { mutableStateOf(0) }
    var average by remember { mutableStateOf(0f) }
    var outcome by remember { mutableStateOf("") }
    var outcomeColor by remember { mutableStateOf(Color.Black) }

    // Outer container for the UI using styled composable (4.2)
    StyledColumn {
        // 4.3) Composable tool for user inputs.
        UserScoreInputField(label = "Test Score 1", value = score1, onValueChange = { score1 = it })
        UserScoreInputField(label = "Test Score 2", value = score2, onValueChange = { score2 = it })

        // 4.4)  Button to trigger calculation.
        CalculateButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            score1 = score1.toIntOrNull() ?: 0, // Convert scores to Int. If the conversion fails, default to 0.
            score2 = score2.toIntOrNull() ?: 0,
            onTotalChange = { total = it },
            onAverageChange = { average = it },
            onOutcomeChange = { outcome = it },
            onOutcomeColorChange = { outcomeColor = it }
        )

        // 4.5) Display composable showing the calculated total, average, and outcome.
        ResultDisplay(total = total, average = average, outcome = outcome, outcomeColor = outcomeColor)
    }
}

// 4.2) First styling composable: A simple styled Column.
@Composable
fun StyledColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    MaterialTheme {
        Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

// 4.2) Second styling composable: A Column variant that allows customizable vertical spacing, color scheme and typography.
@Composable
fun StyledColumn(
    modifier: Modifier = Modifier,
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    typography: Typography = MaterialTheme.typography,
    verticalSpacing: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    MaterialTheme(colorScheme = colorScheme, typography = typography) {
        Column(
            modifier = modifier.fillMaxSize().padding(vertical = verticalSpacing, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(verticalSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

// 4.3) Composable tool to handle user input with a label and styled TextField.
@Composable
fun UserScoreInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    val score = value.toIntOrNull() ?: 0
    val isError = score < 0 || score > 100
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        isError = isError,
        singleLine = true,
        supportingText = if (isError) {{
            Text(
                text = "Score must range between 0 and 100",
                color = MaterialTheme.colorScheme.error
            )
        }} else null
    )
    Spacer(modifier = Modifier.height(8.dp))
}

// 4.4) Calculate Button to total and average when the button is clicked.
@Composable
fun CalculateButton(
    modifier: Modifier = Modifier,
    score1: Int,
    score2: Int,
    onTotalChange: (Int) -> Unit,
    onAverageChange: (Float) -> Unit,
    onOutcomeChange: (String) -> Unit,
    onOutcomeColorChange: (Color) -> Unit,
) {
    Button(
        onClick = {
            // Calculate total and average
            val total = score1 + score2
            onTotalChange(total)
            val average = total / 2f
            onAverageChange(average)

            // Determine an outcome and set text color based on average.
            val outcome = when {
                average == 100f -> "Cum Laude"
                average > 79f -> "Pass with Merit"
                average > 74f -> "Pass with Distinction"
                average >= 50f -> "Pass"
                else -> "Fail"
            }
            onOutcomeChange(outcome)
            val outcomeColor = when {
                average > 74f -> Color.Green
                average >= 50f -> Color.Blue
                else -> Color.Red
            }
            onOutcomeColorChange(outcomeColor)
        },
        modifier = modifier
    ) {
        Text("Calculate")
    }
}

// 4.5) Composable to display the calculated total, average, and performance outcome.
@Composable
fun ResultDisplay(total: Int, average: Float, outcome: String, outcomeColor: Color) {
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Total: $total",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Average: ${"%.2f".format(average)}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = outcome,
            color = outcomeColor,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
    }
}
