package com.thedonorzone.thedonorzone.model

enum class EnumState(val displayName: String) {
    NEW("New"),
    PERFECT("Perfect"),
    GOOD ("Good condition");
    override fun toString(): String {
        return displayName
    }
}