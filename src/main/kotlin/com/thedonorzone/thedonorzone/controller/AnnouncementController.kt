package com.thedonorzone.thedonorzone.controller

import com.thedonorzone.thedonorzone.dto.AnnouncementDto
import com.thedonorzone.thedonorzone.dto.FavoritesDto
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
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/announcements")
class AnnouncementController(private val announcementService: AnnouncementService) {
    @PostMapping("/create")
    fun create (@ModelAttribute announcementDto: AnnouncementDto, model: Model, @SessionAttribute(name = "currentUser") user: User?): String {
        return try {
            if (user == null ||user.username == "") {
                return "redirect:/login" // Rediriger si aucun utilisateur n'est connecté
            }
            val announcement =  announcementService.createAnnouncement(announcementDto, user.username!!)
            model.addAttribute("message", "Annonce créée avec succès !")
            return "confirmation" // Retourne une page de confirmation
        } catch (e: RuntimeException) {
            println("Erreur lors de la création de l'annonce : ${e.message}")
            return "error"
        }
    }

    @GetMapping("/Formulaire.html")
    fun formulaire(): String {
        return "Formulaire" 
    }

    @GetMapping("/index.html")
    fun index(): String {
        return "index" // Spring recherche "Formulaire.html" dans src/main/resources/templates
    }
    

    @GetMapping("") 
    fun getAnnouncements(model: Model, @RequestParam(required = false)geographicalArea : String?, @RequestParam(required = false)state : String?, @RequestParam(required = false)donation : String?, @RequestParam(required = false) keywords: String? ): String {
            return try {
                if (geographicalArea=="")
                {
                    val announcements =  announcementService.getAnnouncements(null,state,donation,keywords)
                    model.addAttribute("announcements", announcements)
                    "Research" 
                }
                else 
                {
                    val announcements =  announcementService.getAnnouncements(geographicalArea,state,donation,keywords)
                    model.addAttribute("announcements", announcements)
                    "Research" 
                }
                val announcements =  announcementService.getAnnouncements(geographicalArea,state,donation,keywords)
                model.addAttribute("announcements", announcements)
                "Research"
            } catch (e: RuntimeException) {
                return "error" 
            }
    }
 
   @GetMapping("/sell")
    fun sell(model: Model, @SessionAttribute(name = "currentUser") user: User?): String { 
            return try {
                if (user == null ||user.username == "") {
                    return "redirect:/login" // Rediriger si aucun utilisateur n'est connecté
                }
                val announcements =  announcementService.getAnnouncementsByusername(user.username!!)
                model.addAttribute("announcements", announcements)
                return "Sell"
            } catch (e: RuntimeException) {
                return "error" 
            }
        }
     
   @PostMapping("/favorites")
        fun create (@RequestBody favoritesDto: FavoritesDto, @SessionAttribute(name = "currentUser") user: User?, model: Model): String {
            return try {
                if (user == null ||user.username == "") {
                    return "redirect:/login" // Rediriger si aucun utilisateur n'est connecté
                }
                val favorites = announcementService.createFavorites(favoritesDto)
                model.addAttribute("message", "Favoris créé avec succès !")
                return "confirmation" // Retourne une page de confirmation
            } catch (e: RuntimeException) {
                return "error"
            }
     }

     @GetMapping("/favorites")
     fun getAllFavoritesByUsername(model: Model, @SessionAttribute(name = "currentUser") user: User?): String {
         return try {
             if (user == null ||user.username == "") {
                 return "redirect:/login" // Rediriger si aucun utilisateur n'est connecté
             }
             val announcementsList = mutableListOf<Announcement>()
             val favorites = announcementService.getAllFavoritesByUsername(user.username!!)
             favorites?.let {
                if (it.isNotEmpty()) {
                    // Parcourir la liste des favoris
                    for (favorite in it) {
                        val idAnnouncement = favorite.idAnnoucement
                        val announcement = announcementService.findById(idAnnouncement)
                        announcement?.let {
                            announcementsList.add(it) // Ajouter l'annonce à la liste si elle existe
                        }
                    }
                }
            }
             model.addAttribute("announcements", announcementsList)
             return "Favorites"
         } catch (e: RuntimeException) {
             return "error" 
         }
    }

    @GetMapping("/{idAnnouncement}")
    fun getById(@PathVariable idAnnouncement: Long, model: Model): String {
        return try {
            val announcement =  announcementService.findById(idAnnouncement)
            model.addAttribute("announcement", announcement)
            return "Annonce"
        } catch (e: IllegalArgumentException) {
            return "error"
        } catch (e: RuntimeException) {
            return "error"
        }
    }

    @RequestMapping(value = ["/{idAnnouncement}"], method = [RequestMethod.POST])
        fun deleteAnnouncementById(@SessionAttribute(name = "currentUser") user: User?, model: Model,@PathVariable idAnnouncement: Long, @RequestParam("_method", required = false) method: String?): String {
        return try {
            if (user == null ||user.id == null) {
                return "redirect:/login" // Rediriger si aucun utilisateur n'est connecté
            }
            val announcement =  announcementService.findById(idAnnouncement)
            if (announcement != null && user.username==announcement.username)
            {
                announcementService.deleteAnnouncementById(idAnnouncement)
                model.addAttribute("message", "Annonce supprimée avec succès !")
                return "confirmation" // Retourne une page de confirmation
            }
            else {
                return "error"
            }
        } catch (e: RuntimeException) {
           return "error"
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
