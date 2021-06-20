package com.mthaler.springjdbc

import com.mthaler.springjdbc.entities.Singer
import org.springframework.jdbc.`object`.MappingSqlQuery
import java.sql.ResultSet
import javax.sql.DataSource

class SelectAllSingers(dataSource: DataSource): MappingSqlQuery<Singer>(dataSource, SQL_SELECT_ALL_SINGER) {

    override fun mapRow(rs: ResultSet, rowNum: Int): Singer = Singer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birth_date"))

    companion object {

        private const val SQL_SELECT_ALL_SINGER = "select id, first_name, last_name, birth_date from singer"
    }
}