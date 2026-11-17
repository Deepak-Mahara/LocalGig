package com.example.localgig.feature.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.localgig.domain.model.Conversation

@Composable
fun MessagesListScreen(
    conversations: List<Conversation>,
    isLocalRole: Boolean,
    onConversationClick: (Conversation) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Messages",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        if (conversations.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No active conversations.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn {
                items(conversations) { conv ->
                    ConversationItem(
                        conversation = conv,
                        isLocalRole = isLocalRole,
                        onClick = { onConversationClick(conv) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun ConversationItem(conversation: Conversation, isLocalRole: Boolean, onClick: () -> Unit) {
    val partnerName = if (isLocalRole) conversation.gigName else conversation.localName
    val lastMessage = conversation.messages.lastOrNull()?.text ?: "New Conversation started"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(12.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = partnerName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Job: ${conversation.jobTitle}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            Text(text = lastMessage, maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}