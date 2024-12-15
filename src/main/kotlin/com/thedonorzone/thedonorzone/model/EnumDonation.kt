package com.thedonorzone.thedonorzone.model

enum class EnumDonation(val displayName: String) {
    HAND("Hand-delivered"),
    SENT("Donation sent");
    override fun toString(): String {
        return displayName
    }
}