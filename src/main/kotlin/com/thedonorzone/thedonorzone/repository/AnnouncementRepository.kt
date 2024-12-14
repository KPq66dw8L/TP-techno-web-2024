package com.thedonorzone.thedonorzone.repository

import com.thedonorzone.thedonorzone.model.Annoucement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@Repository
interface AnnouncementRepository : JpaRepository<Annoucement, Long> {
    fun getAnnouncementsByIdUser(idUser: Long): List<Annoucement>
    @Query("""
    SELECT r FROM Annoucement r 
    WHERE (:geographicalArea IS NULL OR r.geographicalArea = :geographicalArea) 
    AND (:state IS NULL OR r.state = :state) AND (:donation IS NULL OR r.donation = :donation)  AND (:keyWord IS NULL OR r.listOfkeyWords LIKE %:keyWord% )
    """)
     fun getAnnouncements(
        @Param("geographicalArea") geographicalArea: String?,
        @Param("state") state: String?,
        @Param("donation") donation: String?,
        @Param("keyWord") keyWord: String?
    ):  List<Annoucement> 
    

    @Query("SELECT a FROM Annoucement a WHERE a.listOfkeyWords LIKE %:keyword%")
    fun getAnnouncementsBySearch(keyword: String): List<Annoucement>
}