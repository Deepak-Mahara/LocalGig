package com.example.localgig.feature.dashboard

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.localgig.domain.model.Conversation
import com.example.localgig.domain.model.GigJob
import com.example.localgig.domain.model.LocalJob
import com.example.localgig.domain.model.Message

class DashboardViewModel : ViewModel() {

    private val _localJobs = mutableStateListOf<LocalJob>(
        LocalJob(
            category = "Plumbing",
            title = "Fix Leaking Kitchen Sink",
            description = "The U-bend pipe under the kitchen sink is heavily leaking water into the cabinet. Need a quick fix or pipe replacement as soon as possible.",
            location = "Block C, Model Town",
            price = "450",
            imageUris = emptyList(),
            posterId = "local_123",
            posterName = "Sarthak V."
        )
    )
    val localJobs: List<LocalJob> = _localJobs

    private val _gigJobs = mutableStateListOf<GigJob>(
        GigJob(
            category = "Electrical",
            title = "Certified Electrician Services",
            description = "Licensed electrician available for ceiling fan installation, wiring repairs, and general household electrical fault finding.",
            workingHours = "10:00 AM - 7:00 PM",
            rate = "300",
            posterId = "gig_456",
            posterName = "Rahul K."
        )
    )
    val gigJobs: List<GigJob> = _gigJobs

    // NEW: Chat State
    private val _conversations = mutableStateListOf<Conversation>()
    val conversations: List<Conversation> = _conversations

    fun addLocalJob(job: LocalJob) { _localJobs.add(job) }
    fun addGigJob(job: GigJob) { _gigJobs.add(job) }
    fun deleteLocalJob(job: LocalJob) { _localJobs.remove(job) }
    fun deleteGigJob(job: GigJob) { _gigJobs.remove(job) }

    // NEW: Chat Functions
    fun getOrCreateConversation(jobId: String, jobTitle: String, localId: String, localName: String, gigId: String, gigName: String): Conversation {
        val existing = _conversations.find { it.jobId == jobId && it.gigId == gigId }
        if (existing != null) return existing

        val newConv = Conversation(
            jobId = jobId, jobTitle = jobTitle,
            localId = localId, localName = localName,
            gigId = gigId, gigName = gigName
        )
        _conversations.add(newConv)
        return newConv
    }

    fun addMessage(conversationId: String, senderId: String, text: String) {
        val index = _conversations.indexOfFirst { it.id == conversationId }
        if (index != -1) {
            val conv = _conversations[index]
            val newMessage = Message(senderId = senderId, text = text)
            // Replace the old conversation with a new copy containing the appended message
            _conversations[index] = conv.copy(messages = conv.messages + newMessage)
        }
    }
}