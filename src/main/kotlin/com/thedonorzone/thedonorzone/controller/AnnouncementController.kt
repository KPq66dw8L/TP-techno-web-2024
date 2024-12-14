package com.thedonorzone.thedonorzone.controller

import com.thedonorzone.thedonorzone.dto.AnnoucementDto
import com.thedonorzone.thedonorzone.model.User
import org.springframework.ui.Model
import com.thedonorzone.thedonorzone.service.AnnoucementService
import org.springframework.http.ResponseEntity
import com.thedonorzone.thedonorzone.model.Annoucement
import com.thedonorzone.thedonorzone.model.AnnoucementUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping("/api/announcement")
class AnnouncementController(private val annoucementService: AnnoucementService) {
    @PostMapping("")
    fun create (@RequestBody annoucementDto: AnnoucementDto): ResponseEntity<Annoucement> {
        return try {
            val annoucement = annoucementService.createAnnoucement(annoucementDto)
            ResponseEntity(annoucement, HttpStatus.CREATED)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.CONFLICT)
        }
    }

    @GetMapping("/search") 
    fun getAnnouncementsBySearch(@RequestParam(required = false)geographicalArea : String?, @RequestParam(required = false)state : String?, @RequestParam(required = false)keyWord : String? ): ResponseEntity<List<Annoucement>> {
        return try {
            val annoucements = annoucementService.getAnnouncementsBySearch(geographicalArea,state,keyWord)
            ResponseEntity(annoucements, HttpStatus.OK)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.CONFLICT)
        }
    }

    @GetMapping("/user/{idUser}")
    fun getAnnouncementsByIdUser (@PathVariable idUser: Long): ResponseEntity<List<Annoucement>> {
        return try {
            val annoucements = annoucementService.getAnnouncementsByIdUser(idUser)
            ResponseEntity(annoucements, HttpStatus.CREATED)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.CONFLICT)
        }
    }

    @GetMapping("")
    fun getAllAnnoucement(model: Model): String {
            return try {
                val announcements = annoucementService.getAllAnnoucement()
                model.addAttribute("announcements", announcements)
                "index" // Retourne le nom du fichier HTML sans extension
            } catch (e: RuntimeException) {
                "error" // Une vue HTML pour afficher les erreurs
            }
        }
    

   /*  @GetMapping("/favorites")
    fun getAllFavorites(): ResponseEntity<List<Annoucement>> {
        return try {
            val favorites = annoucementService.getAllFavorites()
            ResponseEntity(favorites, HttpStatus.OK)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.OK)
        }
    } */

    @GetMapping("/{idAnnoucement}")
    fun getById(@PathVariable idAnnoucement: Long): ResponseEntity<Annoucement> {
        return try {
            val annoucement = annoucementService.findById(idAnnoucement)
           /*  if (annoucement == null)
            {
                ResponseEntity(annoucement,HttpStatus.NOT_FOUND)
            }*/
            ResponseEntity(annoucement, HttpStatus.OK)
        } catch (e: IllegalArgumentException) {
                // Gérer une exception liée à un ID invalide
            throw RuntimeException("ID invalide", e)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
    @DeleteMapping("/{idAnnoucement}")
        fun deleteAnnoucementById(@PathVariable idAnnoucement: Long): ResponseEntity<Annoucement> {
        return try {
            annoucementService.deleteAnnoucementById(idAnnoucement)
            ResponseEntity(null, HttpStatus.NO_CONTENT)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.OK)
        }
    }

    @PutMapping("/{idAnnoucement}") 
    fun updateAnnoucement(@PathVariable idAnnoucement: Long, @RequestBody updateRequest: AnnoucementUpdateRequest): ResponseEntity<Annoucement> {
        return try {
            val updatedAnnoucement = annoucementService.updateAnnoucement(idAnnoucement, updateRequest)
            ResponseEntity(updatedAnnoucement, HttpStatus.OK)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.CONFLICT)
        }
    }
}

