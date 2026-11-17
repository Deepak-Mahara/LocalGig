package com.example.localgig.feature.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.localgig.domain.model.Conversation
import com.example.localgig.domain.model.GigJob
import com.example.localgig.domain.model.LocalJob
import com.example.localgig.feature.dashboard.components.*

// feature/dashboard/DashboardScreen.kt

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel(),
    initialIsLocalRole: Boolean = true // ADD THIS PARAMETER
) {
    // Initialize the state with the passed parameter
    var isLocalRole by remember { mutableStateOf(initialIsLocalRole) }

    // ... rest of your DashboardScreen code remains the same ...
    var selectedBottomTab by remember { mutableIntStateOf(0) }
    var selectedJobSubTab by remember { mutableIntStateOf(0) }

    var selectedLocalJob by remember { mutableStateOf<LocalJob?>(null) }
    var selectedGigJob by remember { mutableStateOf<GigJob?>(null) }
    var viewedPublicProfileName by remember { mutableStateOf<String?>(null) }

    var activeConversation by remember { mutableStateOf<Conversation?>(null) }

    val currentUserId = if (isLocalRole) "local_123" else "gig_456"
    val currentUserName = if (isLocalRole) "Sarthak V." else "Rahul K."

    if (activeConversation != null) {
        val latestConv = viewModel.conversations.find { it.id == activeConversation!!.id } ?: activeConversation!!

        StructuredChatScreen(
            conversation = latestConv,
            currentUserId = currentUserId,
            isLocalRole = isLocalRole,
            onBack = { activeConversation = null },
            onSendMessage = { text ->
                viewModel.addMessage(latestConv.id, currentUserId, text)
            }
        )
        return
    }

    if (viewedPublicProfileName != null) {
        ProfileScreen(
            isLocalRole = isLocalRole,
            userName = viewedPublicProfileName!!,
            isReadOnly = true,
            onBack = { viewedPublicProfileName = null }
        )
        return
    }

    if (selectedLocalJob != null) {
        JobDetailScreen(
            job = selectedLocalJob!!,
            isLocalRole = isLocalRole,
            onBack = { selectedLocalJob = null },
            onMessageClick = {
                val newOrExistingConv = viewModel.getOrCreateConversation(
                    jobId = selectedLocalJob!!.id,
                    jobTitle = selectedLocalJob!!.title,
                    localId = selectedLocalJob!!.posterId,
                    localName = selectedLocalJob!!.posterName,
                    gigId = currentUserId,
                    gigName = currentUserName
                )
                activeConversation = newOrExistingConv
                selectedLocalJob = null
            },
            onDeleteClick = {
                viewModel.deleteLocalJob(selectedLocalJob!!)
                selectedLocalJob = null
            },
            onViewProfileClick = { posterName ->
                viewedPublicProfileName = posterName
            }
        )
        return
    }

    if (selectedGigJob != null) {
        GigJobDetailScreen(
            job = selectedGigJob!!,
            isLocalRole = isLocalRole,
            currentUserId = currentUserId,
            onBack = { selectedGigJob = null },
            onMessageClick = {
                val newOrExistingConv = viewModel.getOrCreateConversation(
                    jobId = selectedGigJob!!.id,
                    jobTitle = selectedGigJob!!.title,
                    localId = currentUserId,
                    localName = currentUserName,
                    gigId = selectedGigJob!!.posterId,
                    gigName = selectedGigJob!!.posterName
                )
                activeConversation = newOrExistingConv
                selectedGigJob = null
            },
            onDeleteClick = {
                viewModel.deleteGigJob(selectedGigJob!!)
                selectedGigJob = null
            },
            onViewProfileClick = { posterId, posterName ->
                if (posterId == currentUserId) {
                    selectedGigJob = null
                    selectedBottomTab = 3
                } else {
                    viewedPublicProfileName = posterName
                }
            }
        )
        return
    }

    Scaffold(
        topBar = {
            DashboardTopBar(
                isLocalRole = isLocalRole,
                onSwitchRole = {
                    isLocalRole = !isLocalRole
                    selectedJobSubTab = 0
                }
            )
        },
        bottomBar = {
            DashboardBottomNav(
                isLocalRole = isLocalRole,
                selectedTab = selectedBottomTab,
                onTabSelected = { selectedBottomTab = it }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedBottomTab) {
                0 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
                    ) {
                        item {
                            RoleInfoCard(isLocalRole = isLocalRole)
                            Spacer(modifier = Modifier.height(16.dp))

                            JobFilterTray(
                                isLocalRole = isLocalRole,
                                selectedTabIndex = selectedJobSubTab,
                                onTabSelected = { selectedJobSubTab = it }
                            )
                            Spacer(modifier = Modifier.height(24.dp))

                            val headerTitle = when (selectedJobSubTab) {
                                0 -> "All Jobs"
                                1 -> "Available Gigs"
                                2 -> if (isLocalRole) "Your Work" else "Assigned Work"
                                else -> "Jobs"
                            }

                            val headerSubtitle = when (selectedJobSubTab) {
                                0 -> "Browse all available jobs in your area"
                                1 -> "Find quick gigs happening right now"
                                2 -> if (isLocalRole) "Manage your requested work" else "Direct bookings confirmed with clients"
                                else -> ""
                            }

                            Text(
                                text = headerTitle,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = headerSubtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        when (selectedJobSubTab) {
                            0 -> {
                                items(viewModel.localJobs) { job ->
                                    LocalJobCard(
                                        job = job,
                                        onViewDetails = { selectedLocalJob = job }
                                    )
                                }
                            }
                            1 -> {
                                items(viewModel.gigJobs) { gig ->
                                    GigJobCard(
                                        job = gig,
                                        onViewDetails = { selectedGigJob = gig }
                                    )
                                }
                            }
                            2 -> {
                                item {
                                    Text(
                                        text = "No assigned work yet.",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                1 -> {
                    if (isLocalRole) {
                        RequestJobForm(
                            onSubmit = { job ->
                                viewModel.addLocalJob(job)
                                selectedBottomTab = 0
                                selectedJobSubTab = 0
                            }
                        )
                    } else {
                        PostJobForm(
                            onSubmit = { gig ->
                                viewModel.addGigJob(gig)
                                selectedBottomTab = 0
                                selectedJobSubTab = 1
                            }
                        )
                    }
                }

                2 -> {
                    MessagesListScreen(
                        conversations = viewModel.conversations.filter { it.localId == currentUserId || it.gigId == currentUserId },
                        isLocalRole = isLocalRole,
                        onConversationClick = { activeConversation = it }
                    )
                }

                3 -> {
                    ProfileScreen(isLocalRole = isLocalRole)
                }
            }
        }
    }
}