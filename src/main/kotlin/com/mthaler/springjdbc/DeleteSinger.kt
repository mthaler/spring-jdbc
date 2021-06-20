package com.mthaler.springjdbc

import org.springframework.jdbc.`object`.SqlUpdate
import org.springframework.jdbc.core.SqlParameter
import java.sql.Types
import javax.sql.DataSource

class DeleteSinger(dataSource: DataSource): SqlUpdate(dataSource, SQL_DELETE_SINGERS) {

    init {
        super.declareParameter(SqlParameter("id", Types.INTEGER))
    }

    companion object {

        private const val SQL_DELETE_SINGERS = "delete from singer where id = :id"
    }
}