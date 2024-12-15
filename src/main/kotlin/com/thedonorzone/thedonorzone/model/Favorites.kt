package com.thedonorzone.thedonorzone.model

import jakarta.persistence.*


@Entity
@Table(name = "favorites")
data class Favorites(
    @Id
    val idAnnoucement: Long = 0,
    val idUser : Long=0
){
    // Constructeur par défaut requis par JPA
    constructor() : this(
        idAnnoucement = 0,
        idUser = 0
    )
}