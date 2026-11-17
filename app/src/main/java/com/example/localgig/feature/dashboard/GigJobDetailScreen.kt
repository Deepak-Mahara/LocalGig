package com.example.localgig.feature.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.localgig.domain.model.GigJob

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GigJobDetailScreen(
    job: GigJob,
    isLocalRole: Boolean,
    currentUserId: String,
    onBack: () -> Unit,
    onMessageClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onViewProfileClick: (String, String) -> Unit
) {
    // Check if the current user owns this gig (Must be in Gig Role AND IDs match)
    val isOwnGig = !isLocalRole && job.posterId == currentUserId

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gig Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(job.category.uppercase(), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            Text(job.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("🕒 Working Hours: ${job.workingHours}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

            HorizontalDivider()

            Text("Service Description", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(job.description, style = MaterialTheme.typography.bodyLarge)

            HorizontalDivider()

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("RATE", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text("₹${job.rate}", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Primary Action Button
                Button(
                    onClick = { if (isOwnGig) onDeleteClick() else onMessageClick() },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isOwnGig) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(if (isOwnGig) "Delete this Gig" else "Message if interested")
                }

                // Profile Action Button
                OutlinedButton(
                    onClick = { onViewProfileClick(job.posterId, job.posterName) },
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Icon(Icons.Default.Person, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isOwnGig) "View My Profile" else "View ${job.posterName}'s Profile")
                }
            }
        }
    }
}