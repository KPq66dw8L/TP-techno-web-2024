package com.thedonorzone.thedonorzone.controller

import com.thedonorzone.thedonorzone.dto.AnnouncementDto
import com.thedonorzone.thedonorzone.model.User
import org.springframework.ui.Model
import com.thedonorzone.thedonorzone.service.AnnouncementService
import org.springframework.http.ResponseEntity
import com.thedonorzone.thedonorzone.model.Announcement
import com.thedonorzone.thedonorzone.model.AnnouncementUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping("/announcements")
class AnnouncementController(private val announcementService: AnnouncementService) {
    @PostMapping("/create")
    fun create (@ModelAttribute announcementDto: AnnouncementDto, model: Model): String {
        return try {
            val announcement =  announcementService.createAnnouncement(announcementDto)
            model.addAttribute("message", "Annonce créée avec succès !")
            return "confirmation" // Retourne une page de confirmation
        } catch (e: RuntimeException) {
           "error"
        }
    }

    @GetMapping("/Formulaire.html")
    fun formulaire(): String {
        return "Formulaire" // Spring recherche "Formulaire.html" dans src/main/resources/templates
    }

    
    @GetMapping("/user/{idUser}")
    fun getAnnouncementsByIdUser (@PathVariable idUser: Long): ResponseEntity<List<Announcement>> {
        return try {
            val announcements =  announcementService.getAnnouncementsByIdUser(idUser)
            ResponseEntity(announcements, HttpStatus.CREATED)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.CONFLICT)
        }
    }

    @GetMapping("") 
    fun getAnnouncements(model: Model, @RequestParam(required = false)geographicalArea : String?, @RequestParam(required = false)state : String?, @RequestParam(required = false)donation : String?, @RequestParam keywords: String? ): String {
            return try {
                val announcements =  announcementService.getAnnouncements(geographicalArea,state,donation,keywords)
                print(announcements)
                model.addAttribute("announcements", announcements)
                "index" // Retourne le nom du fichier HTML sans extension
            } catch (e: RuntimeException) {
                "error" // Une vue HTML pour afficher les erreurs
            }
    }
    @GetMapping("/sell")
    fun sell(model: Model): String {
        return "Sell" // Correspond à templates/sell.html
    }
    
   /*  @PostMapping("/favorites")
        fun create (@RequestBody favoritesDto: FavoritesDto): ResponseEntity<Favorites> {
            return try {
                val favorites = annoucementService.createFavorites(FavoritesDto)
                ResponseEntity(favorites, HttpStatus.CREATED)
            } catch (e: RuntimeException) {
                ResponseEntity(HttpStatus.CONFLICT)
            }
     }*/
   /*  @GetMapping("/favorites")
    fun getAllFavorites(): ResponseEntity<List<Annoucement>> {
        return try {
            val favorites = annoucementService.getAllFavorites()
            ResponseEntity(favorites, HttpStatus.OK)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.OK)
        }
    } */

    @GetMapping("/{idAnnouncement}")
    fun getById(@PathVariable idAnnouncement: Long, model: Model): String {
        return try {
            val announcement =  announcementService.findById(idAnnouncement)
           /*  if (annoucement == null)
            {
                ResponseEntity(annoucement,HttpStatus.NOT_FOUND)
            }*/
            model.addAttribute("announcement", announcement)
            return "Annonce"
        } catch (e: IllegalArgumentException) {
                // Gérer une exception liée à un ID invalide
            return "error"
        } catch (e: RuntimeException) {
            return "error"
        }
    }
    @DeleteMapping("/{idAnnouncement}")
        fun deleteAnnouncementById(@PathVariable idAnnouncement: Long): ResponseEntity<Announcement> {
        return try {
            announcementService.deleteAnnouncementById(idAnnouncement)
            ResponseEntity(null, HttpStatus.NO_CONTENT)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.OK)
        }
    }

    @PutMapping("/{idAnnouncement}") 
    fun updateAnnouncement(@PathVariable idAnnouncement: Long, @RequestBody updateRequest: AnnouncementUpdateRequest): ResponseEntity<Announcement> {
        return try {
            val updatedAnnouncement =  announcementService.updateAnnouncement(idAnnouncement, updateRequest)
            ResponseEntity(updatedAnnouncement, HttpStatus.OK)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.CONFLICT)
        }
    }
}

