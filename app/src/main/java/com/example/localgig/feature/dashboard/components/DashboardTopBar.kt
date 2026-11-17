package com.example.localgig.feature.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.localgig.R

@Composable
fun DashboardTopBar(
    isLocalRole: Boolean,
    onSwitchRole: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Your custom images replacing the standard icons
            val imageRes = if (isLocalRole) {
                R.drawable.local_logo // Replace with your Local image name
            } else {
                R.drawable.gig_logo   // Replace with your Gig image name
            }

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Logo",
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isLocalRole) "LocalGig" else "GigLocal",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = if (isLocalRole) "SV" else "RK",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            OutlinedButton(onClick = onSwitchRole) {
                Icon(Icons.Default.SwapHoriz, contentDescription = "Switch Role")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Switch\nRole", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}