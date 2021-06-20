package com.mthaler.springjdbc.dao

import com.mthaler.springjdbc.*
import com.mthaler.springjdbc.entities.Album
import com.mthaler.springjdbc.entities.Singer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import java.sql.ResultSet
import javax.annotation.Resource
import javax.sql.DataSource

abstract class JdbcSingerDao: SingerDao {

    private lateinit var dataSource: DataSource
    private lateinit var selectAllSingers: SelectAllSingers
    private lateinit var selectSingerByFirstName: SelectSingerByFirstName
    private lateinit var updateSinger: UpdateSinger
    private lateinit var insertSinger: InsertSinger
    private lateinit var insertSingerAlbum: InsertSingerAlbum
    private lateinit var deleteSinger: DeleteSinger
    private lateinit var storedFunctionFirstNameById: StoredFunctionFirstNameById

    override fun findAll(): List<Singer> {
        return selectAllSingers.execute()
    }

    override fun findByFirstName(firstName: String): List<Singer> {
        val paramMap: MutableMap<String, Any> = HashMap()
        paramMap["first_name"] = firstName
        return selectSingerByFirstName.executeByNamedParam(paramMap)
    }

    override fun findFirstNameById(id: Long): String {
        val result = storedFunctionFirstNameById.execute(id)
        return result[0]
    }

    override fun insert(singer: Singer) {
        val paramMap: MutableMap<String, Any> = HashMap()
        paramMap["first_name"] = singer.firstName
        paramMap["last_name"] = singer.lastName
        paramMap["birth_date"] = singer.birthDate
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        insertSinger.updateByNamedParam(paramMap, keyHolder)
        singer.id = keyHolder.key!!.toLong()
        logger.info("New singer inserted with id: " + singer.id)
    }

    override fun insertWithAlbum(singer: Singer) {
        insertSingerAlbum = InsertSingerAlbum(dataSource)
        var paramMap: MutableMap<String, Any> = HashMap()
        paramMap["first_name"] = singer.firstName
        paramMap["last_name"] = singer.lastName
        paramMap["birth_date"] = singer.birthDate
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        insertSinger.updateByNamedParam(paramMap, keyHolder)
        singer.id = keyHolder.key!!.toLong()
        logger.info("New singer inserted with id: " + singer.id)
        for ((_, _, title, releaseDate) in singer.albums) {
            paramMap = HashMap()
            paramMap["singer_id"] = singer.id
            paramMap["title"] = title
            paramMap["release_date"] = releaseDate
            insertSingerAlbum.updateByNamedParam(paramMap)
        }
        insertSingerAlbum.flush()
    }

    override fun findAllWithAlbums(): List<Singer> {
        val jdbcTemplate = JdbcTemplate(getDataSource())
        val sql = "SELECT s.id, s.first_name, s.last_name, s.birth_date" +
                ", a.id AS album_id, a.title, a.release_date FROM singer s " +
                "LEFT JOIN album a ON s.id = a.singer_id"
        return jdbcTemplate.query<ArrayList<Singer>>(sql) { rs: ResultSet ->
            val map: MutableMap<Long, Singer> = HashMap()
            var singer: Singer?
            while (rs.next()) {
                val id = rs.getLong("id")
                singer = map[id]
                if (singer == null) {
                    singer = Singer(id, rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birth_date"))
                    map[id] = singer
                }
                val albumId = rs.getLong("album_id")
                if (albumId > 0) {
                    val album = Album(albumId, id, rs.getString("title"), rs.getDate("release_date"))
                    singer.albums.add(album)
                }
            }
            ArrayList(map.values)
        } ?: emptyList()
    }

    @Resource(name = "dataSource")
    fun setDataSource(dataSource: DataSource) {
        this.dataSource = dataSource
        selectAllSingers = SelectAllSingers(dataSource)
        selectSingerByFirstName = SelectSingerByFirstName(dataSource)
        updateSinger = UpdateSinger(dataSource)
        insertSinger = InsertSinger(dataSource)
        storedFunctionFirstNameById = StoredFunctionFirstNameById(dataSource)
        deleteSinger = DeleteSinger(dataSource)
    }

    fun getDataSource(): DataSource {
        return dataSource
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(JdbcSingerDao::class.java)
    }
}