package com.example.localgig.feature.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onLoginAsLocal: () -> Unit,
    onLoginAsGig: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to LocalGig",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "How would you like to use the app today?",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onLoginAsLocal,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Enter as a Local (Find Workers)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onLoginAsGig,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Enter as a Gig Worker (Find Work)")
        }
    }
}