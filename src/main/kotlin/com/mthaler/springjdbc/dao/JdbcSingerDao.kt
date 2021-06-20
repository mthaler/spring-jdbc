package com.mthaler.springjdbc.dao

import com.mthaler.springjdbc.*
import com.mthaler.springjdbc.entities.Singer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
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