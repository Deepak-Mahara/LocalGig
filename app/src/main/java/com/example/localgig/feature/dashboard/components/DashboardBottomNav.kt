package com.example.localgig.feature.dashboard.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun DashboardBottomNav(
    isLocalRole: Boolean,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val items = if (isLocalRole) {
        listOf("Jobs", "Request Job", "Messages", "Profile")
    } else {
        listOf("Jobs", "Post Work", "Messages", "Profile")
    }

    val icons = listOf(
        Icons.Default.WorkOutline,
        Icons.Default.PostAdd, // Used PostAdd as it represents both requesting and posting well
        Icons.Default.Message,
        Icons.Default.PersonOutline
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = item) },
                label = {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) }
            )
        }
    }
}