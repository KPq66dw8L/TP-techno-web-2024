package com.thedonorzone.thedonorzone.dto

import com.thedonorzone.thedonorzone.model.EnumDonation
import com.thedonorzone.thedonorzone.model.EnumState
import java.time.LocalDateTime

data class AnnouncementDto (
    val title : String,
    val description : String,
    val publicationDate : LocalDateTime?,
    val state : String,
    val geographicalArea : String,
    val donation : String,
    val listOfkeyWords : String
)