package com.mthaler.springjdbc

import org.springframework.jdbc.`object`.SqlUpdate
import org.springframework.jdbc.core.SqlParameter
import java.sql.Types
import javax.sql.DataSource

class UpdateSinger(dataSource: DataSource): SqlUpdate(dataSource, SQL_UPDATE_SINGER) {

    init {
        super.declareParameter(SqlParameter("first_name", Types.VARCHAR))
        super.declareParameter(SqlParameter("last_name", Types.VARCHAR))
        super.declareParameter(SqlParameter("birth_date", Types.DATE))
        super.declareParameter(SqlParameter("id", Types.INTEGER))
    }

    companion object {

        private const val SQL_UPDATE_SINGER =
            "update singer set first_name=:first_name, last_name=:last_name, birth_date=:birth_date where id=:id"

    }
}