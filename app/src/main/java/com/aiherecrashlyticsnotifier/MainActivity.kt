package com.aiherecrashlyticsnotifier

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.aiherecrashlyticsnotifier.ui.theme.AIHereCrashlyticsNotifierTheme
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.sentry.Sentry

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AIHereCrashlyticsNotifierTheme {
                MainScreen()
            }
        }
    }
}

fun wrongCalculation(context: Context) {
    // Intentionally wrong calculation: division by zero
    val numerator = 10
    val denominator = 0
    try {
        val result = numerator / denominator
    } catch (e: ArithmeticException) {
        throw RuntimeException("Wrong calculation: Division by zero", e)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("TroubleSoup", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Crash and Error Test Lab",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Use the buttons below to trigger different kinds of crashes and non-fatal errors to verify Sentry and Crashlytics integration.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ElevatedButton(
                onClick = {
                    // 1) Force a fatal crash to test Sentry + Crashlytics crash capture
                    throw RuntimeException("Test fatal crash from UI button")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Trigger Fatal Crash")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    // 2) Record a non-fatal exception to Crashlytics
                    val exception = IllegalStateException("Non-fatal: Crashlytics logged exception")
                    FirebaseCrashlytics.getInstance().recordException(exception)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log Non‑Fatal to Crashlytics")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    // 3) Capture a message and exception in Sentry without crashing
                    Sentry.captureMessage("Non-fatal test message from UI button")
                    val exception = IllegalArgumentException("Non-fatal: Sentry captured exception")
                    Sentry.captureException(exception)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Capture Non‑Fatal in Sentry")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    // 4) Call wrong calculation function and throw exception
                    wrongCalculation(context)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Trigger Wrong Calculation Exception")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AIHereCrashlyticsNotifierTheme {
        MainScreen()
    }
}