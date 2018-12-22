package com.github.butul0ve.spacexchecker.utils

fun String.convertYoutubeUrlToVideoId(): String {
    var result = this.substringAfterLast("/")

    if (result.contains("=")) {
        result = result.substringAfterLast("=")
    }

    return result
}