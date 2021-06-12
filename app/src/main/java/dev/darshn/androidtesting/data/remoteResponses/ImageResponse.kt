package dev.darshn.androidtesting.data.remoteResponses

data class ImageResponse(
    val hits: List<ImageResult>,
    val total : Int,
    val totalHits: Int
)
