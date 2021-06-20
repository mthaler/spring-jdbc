package com.mthaler.springjdbc

import com.mthaler.springjdbc.entities.Singer
import org.springframework.jdbc.`object`.MappingSqlQuery
import org.springframework.jdbc.core.SqlParameter
import java.sql.ResultSet
import java.sql.Types
import javax.sql.DataSource

class SelectSingerByFirstName(dataSource: DataSource): MappingSqlQuery<Singer>(dataSource, SQL_FIND_BY_FIRST_NAME) {

    init {
        super.declareParameter(SqlParameter("first_name", Types.VARCHAR))
    }

    override fun mapRow(rs: ResultSet, rowNum: Int): Singer = Singer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birth_date"))

    companion object {
        private const val SQL_FIND_BY_FIRST_NAME =
            "select id, first_name, last_name, birth_date from singer where first_name = :first_name"
    }
}