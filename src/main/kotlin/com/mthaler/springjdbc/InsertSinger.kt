package com.mthaler.springjdbc

import org.springframework.jdbc.`object`.SqlUpdate
import org.springframework.jdbc.core.SqlParameter
import java.sql.Types
import javax.sql.DataSource

class InsertSinger(dataSource: DataSource): SqlUpdate(dataSource, SQL_INSERT_SINGER) {

    init {
        super.declareParameter(SqlParameter("first_name", Types.VARCHAR))
        super.declareParameter(SqlParameter("last_name", Types.VARCHAR))
        super.declareParameter(SqlParameter("birth_date", Types.DATE))
        super.setGeneratedKeysColumnNames(*arrayOf("id"))
        super.setReturnGeneratedKeys(true)
    }

    companion object {
        private const val SQL_INSERT_SINGER =
            "insert into singer (first_name, last_name, birth_date) values (:first_name, :last_name, :birth_date)"
    }
}