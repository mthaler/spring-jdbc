package com.mthaler.springjdbc.entities

import java.util.*
import kotlin.collections.ArrayList

data class Singer(var id: Long, val firstName: String, val lastName: String, val birthDate: Date, val albums: MutableList<Album> = ArrayList()) {

    fun addAlbum(album: Album): Boolean {
        if (albums.contains(album)) {
            return false
        } else {
            albums.add(album)
            return true
        }
    }
}