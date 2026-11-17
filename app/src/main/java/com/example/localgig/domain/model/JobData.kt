package com.example.localgig.domain.model

import java.util.UUID

data class LocalJob(
    val id: String = UUID.randomUUID().toString(),
    val category: String,
    val title: String,
    val description: String,
    val location: String,
    val price: String,
    val imageUris: List<String> = emptyList(),
    // NEW: User mapping
    val posterId: String = "local_123",
    val posterName: String = "Sarthak V."
)

data class GigJob(
    val id: String = UUID.randomUUID().toString(),
    val category: String,
    val title: String,
    val description: String,
    val workingHours: String,
    val rate: String,
    // NEW: User mapping
    val posterId: String = "gig_456",
    val posterName: String = "Rahul K."
)