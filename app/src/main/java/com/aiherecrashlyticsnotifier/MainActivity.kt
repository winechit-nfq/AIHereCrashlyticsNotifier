package com.aiherecrashlyticsnotifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Hello $name!"
        )
        Button(onClick = {
            // Trigger a Sentry test crash
            throw RuntimeException("Test Sentry crash")
        }) {
            Text("Test Sentry Crash")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AIHereCrashlyticsNotifierTheme {
        Greeting("Android")
    }
}