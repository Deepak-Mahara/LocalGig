package com.example.localgig.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.localgig.domain.model.Conversation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StructuredChatScreen(
    conversation: Conversation,
    currentUserId: String,
    isLocalRole: Boolean,
    onBack: () -> Unit,
    onSendMessage: (String) -> Unit
) {
    val partnerName = if (isLocalRole) conversation.gigName else conversation.localName

    // Predefined Prompts based on Role
    val options = if (isLocalRole) {
        listOf("Yes, it is still available.", "Please bring your own tools.", "I will provide the tools.", "Let's lock it in.")
    } else {
        listOf("Is this job still available?", "What is the exact location?", "Do I need to bring my own tools?", "I can start immediately.")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(partnerName, fontWeight = FontWeight.Bold)
                        Text("Re: ${conversation.jobTitle}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                },
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
        ) {
            // Chat Messages
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                reverseLayout = false
            ) {
                items(conversation.messages) { message ->
                    val isMine = message.senderId == currentUserId
                    ChatBubble(text = message.text, isMine = isMine)
                }
            }

            // Structured Input Tray
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(vertical = 12.dp)) {
                    Text(
                        "Tap a quick reply:",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(options) { option ->
                            AssistChip(
                                onClick = { onSendMessage(option) },
                                label = { Text(option) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(text: String, isMine: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (isMine) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
                .widthIn(max = 250.dp)
        ) {
            Text(
                text = text,
                color = if (isMine) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}