package com.thedonorzone.thedonorzone.repository

import com.thedonorzone.thedonorzone.model.Announcement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@Repository
interface AnnouncementRepository : JpaRepository<Announcement, Long> {
    fun getAnnouncementsByIdUser(idUser: Long): List<Announcement>
    @Query("""
    SELECT r FROM Announcement r 
    WHERE (:geographicalArea IS NULL OR r.geographicalArea = :geographicalArea) 
    AND (:state IS NULL OR r.state = :state) AND (:donation IS NULL OR r.donation = :donation)  AND (:keywords IS NULL OR r.listOfkeyWords LIKE %:keywords% )
    """)
     fun getAnnouncements(
        @Param("geographicalArea") geographicalArea: String?,
        @Param("state") state: String?,
        @Param("donation") donation: String?,
        @Param("keywords") keywords: String?
    ):  List<Announcement> 
    

    @Query("SELECT a FROM Announcement a WHERE a.listOfkeyWords LIKE %:keyword%")
    fun getAnnouncementsBySearch(keyword: String): List<Announcement>
}