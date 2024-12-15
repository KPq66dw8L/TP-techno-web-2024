package com.thedonorzone.thedonorzone.model

import jakarta.persistence.*
import java.time.LocalDateTime

data class AnnouncementUpdateRequest(
    val title: String?,
    val description: String?,
    val state: String?,
    val geographicalArea: String?
)