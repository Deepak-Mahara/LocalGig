package com.example.localgig.domain.model

import java.util.UUID

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val senderId: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class Conversation(
    val id: String = UUID.randomUUID().toString(),
    val jobId: String,
    val jobTitle: String,
    val localId: String,
    val localName: String,
    val gigId: String,
    val gigName: String,
    val messages: List<Message> = emptyList()
)