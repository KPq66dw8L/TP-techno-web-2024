package com.thedonorzone.thedonorzone.service

import com.thedonorzone.thedonorzone.dto.AnnouncementDto
import com.thedonorzone.thedonorzone.model.Announcement
import com.thedonorzone.thedonorzone.model.EnumDonation
import com.thedonorzone.thedonorzone.model.EnumState
import com.thedonorzone.thedonorzone.model.User
import com.thedonorzone.thedonorzone.repository.AnnouncementRepository
import com.thedonorzone.thedonorzone.repository.UserRepository
import org.springframework.stereotype.Service
import com.thedonorzone.thedonorzone.model.AnnouncementUpdateRequest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class AnnouncementService(private val announcementRepository: AnnouncementRepository) {

    fun createAnnouncement(AnnouncementDto: AnnouncementDto): Announcement {
        // TO DO : check if the user is connected
        val publicationDate = AnnouncementDto.publicationDate ?: LocalDateTime.now()
        val announcement= Announcement(title = AnnouncementDto.title, description = AnnouncementDto.description
            ,state = AnnouncementDto.state,geographicalArea = AnnouncementDto.geographicalArea,
            donation = AnnouncementDto.donation, listOfkeyWords = AnnouncementDto.listOfkeyWords, publicationDate = publicationDate)
        return announcementRepository.save(announcement)
    }

   /* fun getAllAnnoucement(): List<Annoucement> {
        return announcementRepository.findAll()
    }
    */ 
   /*fun getAnnouncementsBySearch(geographicalArea: String?, state: String?, keyWord: String?): List<Annoucement> {
        return announcementRepository.getAnnouncementsBySearch(geographicalArea, state, keyWord)
    }*/
    /*fun getAnnouncementsBySearch(keyWord: String): List<Annoucement> {
        return announcementRepository.getAnnouncementsBySearch(keyWord)
    }*/

    fun getAnnouncements(geographicalArea: String?, state: String?, donation: String?, keywords: String?): List<Announcement> {
        return announcementRepository.getAnnouncements(geographicalArea, state, donation, keywords)
    }
    fun findById(idAnnouncement: Long): Announcement? {
        return announcementRepository.findById(idAnnouncement).orElse(null) 
    }
    
    fun getAnnouncementsByIdUser(idUser: Long): List<Announcement> {
        return announcementRepository.getAnnouncementsByIdUser(idUser)
    }

    fun deleteAnnouncementById(idAnnouncement: Long) {
        announcementRepository.deleteById(idAnnouncement)
    }

    fun updateAnnouncement(idAnnouncement: Long, updateRequest: AnnouncementUpdateRequest): Announcement? {
        // Vérifier si l'entité existe
        val existingAnnouncement = announcementRepository.findById(idAnnouncement).orElse(null) ?: return null

        // Mettre à jour uniquement les champs nécessaires
        val updatedAnnouncement = existingAnnouncement.copy(
            title = updateRequest.title ?: existingAnnouncement.title,
            description = updateRequest.description ?: existingAnnouncement.description,
            state = updateRequest.state ?: existingAnnouncement.state,
            geographicalArea = updateRequest.geographicalArea ?: existingAnnouncement.geographicalArea
        )

        // Sauvegarder et retourner l'entité mise à jour
        return announcementRepository.save(updatedAnnouncement)
    }
}
