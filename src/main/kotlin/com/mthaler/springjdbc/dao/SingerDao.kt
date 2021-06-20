package com.mthaler.springjdbc.dao

import com.mthaler.springjdbc.entities.Singer

interface SingerDao {

    fun findAll(): List<Singer>

    fun findByFirstName(firstName: String): List<Singer>

    fun findNameById(id: Long): String

    fun findLastNameById(id: Long): String

    fun findFirstNameById(id: Long): String

    fun insert(singer: Singer)

    fun update(singer: Singer)

    fun delete(singerId: Long)

    fun findAllWithAlbums(): List<Singer>

    fun insertWithAlbum(singer: Singer)
}