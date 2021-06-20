package com.mthaler.springjdbc

import com.mthaler.springjdbc.config.AppConfig
import com.mthaler.springjdbc.dao.SingerDao
import com.mthaler.springjdbc.entities.Album
import com.mthaler.springjdbc.entities.Singer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.GenericApplicationContext
import java.util.*
import java.util.function.Consumer


class AnnotationJdbcTest {

    private lateinit var ctx: GenericApplicationContext
    private lateinit var singerDao: SingerDao

    @BeforeEach
    fun setUp() {
        ctx = AnnotationConfigApplicationContext(AppConfig::class.java)
        singerDao = ctx.getBean(SingerDao::class.java)
        assertNotNull(singerDao)
    }

    @Test
    fun testFindAll() {
        val singers = singerDao.findAll()
        assertTrue(singers.size == 3)
        listSingers(singers)
        ctx.close()
    }

    @Test
    fun testFindByFirstName() {
        val singers = singerDao.findByFirstName("John")
        assertTrue(singers.size == 1)
        listSingers(singers)
        ctx.close()
    }

    @Test
    fun testSingerUpdate() {
        val singer = Singer(1L, "John Clayton", "Mayer", Date(
            GregorianCalendar(1977, 9, 16).time.time
        ))
        singerDao.update(singer)
        val singers = singerDao.findAll()
        listSingers(singers)
    }

    @Test
    fun testSingerInsert() {
        val singer = Singer(0L, "Ed", "Sheeran", Date(
            GregorianCalendar(1991, 1, 17).time.time
        ))
        singerDao.insert(singer)
        val singers = singerDao.findAll()
        listSingers(singers)
    }

    @Test
    fun testSingerInsertWithAlbum() {
        val singer = Singer(0L, "BB", "King", Date(
            GregorianCalendar(1940, 8, 16).time.time
        ))
        var album = Album(0L, 0L,"My Kind of Blues", Date(
            GregorianCalendar(1961, 7, 18).time.time
        ))
        singer.addAlbum(album)
        album = Album(0L, 0L, "A Heart Full of Blues", Date(
            GregorianCalendar(1962, 3, 20).time.time
        ))
        singer.addAlbum(album)
        singerDao.insertWithAlbum(singer)
        val singers = singerDao.findAllWithAlbums()
        listSingers(singers)
    }

    @Test
    fun testFindFirstNameById() {
        val firstName = singerDao.findFirstNameById(2L)
        assertEquals("Eric", firstName)
        println("Retrieved value: $firstName")
    }


    private fun listSingers(singers: List<Singer>) {
        singers.forEach(Consumer { singer: Singer ->
            println(singer)
            if (singer.albums != null) {
                for (album in singer.albums) {
                    println("\t--> $album")
                }
            }
        })
    }

    @AfterEach
    fun tearDown() {
        ctx.close()
    }
}