package com.thedonorzone.thedonorzone.service

import com.thedonorzone.thedonorzone.dto.AnnouncementDto
import com.thedonorzone.thedonorzone.model.Annoucement
import com.thedonorzone.thedonorzone.model.EnumDonation
import com.thedonorzone.thedonorzone.model.EnumState
import com.thedonorzone.thedonorzone.model.User
import com.thedonorzone.thedonorzone.repository.AnnouncementRepository
import com.thedonorzone.thedonorzone.repository.UserRepository
import org.springframework.stereotype.Service
import com.thedonorzone.thedonorzone.model.AnnoucementUpdateRequest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class AnnoucementService(private val announcementRepository: AnnouncementRepository) {

    fun createAnnoucement(AnnouncementDto: AnnouncementDto): Annoucement {
        // TO DO : check if the user is connected
        val publicationDate = AnnouncementDto.publicationDate ?: LocalDateTime.now()
        val annoucement= Annoucement(title = AnnouncementDto.title, description = AnnouncementDto.description
            ,state = AnnouncementDto.state,geographicalArea = AnnouncementDto.geographicalArea,
            donation = AnnouncementDto.donation, listOfkeyWords = AnnouncementDto.listOfkeyWords, publicationDate = publicationDate)
        return announcementRepository.save(annoucement)
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

    fun getAnnouncements(geographicalArea: String?, state: String?, donation: String?, keyWord: String?): List<Annoucement> {
        return announcementRepository.getAnnouncements(geographicalArea, state, donation, keyWord)
    }
    fun findById(idAnnoucement: Long): Annoucement? {
        // TO DO : check if the user is connected
        println("testici\n\n")
        return announcementRepository.findById(idAnnoucement).orElse(null) 
    }
    
    fun getAnnouncementsByIdUser(idUser: Long): List<Annoucement> {
        return announcementRepository.getAnnouncementsByIdUser(idUser)
    }

    fun deleteAnnoucementById(idAnnoucement: Long) {
        announcementRepository.deleteById(idAnnoucement)
    }

    fun updateAnnoucement(idAnnoucement: Long, updateRequest: AnnoucementUpdateRequest): Annoucement? {
        // Vérifier si l'entité existe
        val existingAnnoucement = announcementRepository.findById(idAnnoucement).orElse(null) ?: return null

        // Mettre à jour uniquement les champs nécessaires
        val updatedAnnoucement = existingAnnoucement.copy(
            title = updateRequest.title ?: existingAnnoucement.title,
            description = updateRequest.description ?: existingAnnoucement.description,
            state = updateRequest.state ?: existingAnnoucement.state,
            geographicalArea = updateRequest.geographicalArea ?: existingAnnoucement.geographicalArea
        )

        // Sauvegarder et retourner l'entité mise à jour
        return announcementRepository.save(updatedAnnoucement)
    }
}
