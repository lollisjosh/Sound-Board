package com.example.soundboard

data class UploadResponse(
    val detail: String,
    val id: String?,
    val filename: String?
)

data class SoundDescriptionResponse(
    val id: Int,
    val status: String,
    val detail: String
)

data class ErrorResponse(
    val detail: String,
    val type: String?,
    val status: Int
)