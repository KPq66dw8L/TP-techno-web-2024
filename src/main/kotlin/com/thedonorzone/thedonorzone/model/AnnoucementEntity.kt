package com.thedonorzone.thedonorzone.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "annoucement")
data class Annoucement(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idAnnoucement: Long = 0,
    val idUser : Long=0,
    val title : String="",
    val description : String="",
    val publicationDate : LocalDateTime=LocalDateTime.now(),
    val state : String="",
    val geographicalArea : String="",
    val donation : String="",
    val listOfkeyWords : String=""
){
    // Constructeur par défaut requis par JPA
    constructor() : this(
        idAnnoucement = 0,
        idUser = 0,
        title = "",
        description = "",
        publicationDate = LocalDateTime.now(),
        state = "",
        geographicalArea = "",
        donation = "",
        listOfkeyWords = ""
    )
}
