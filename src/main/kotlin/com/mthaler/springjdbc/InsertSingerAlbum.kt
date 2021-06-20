package com.mthaler.springjdbc

import org.springframework.jdbc.`object`.BatchSqlUpdate
import org.springframework.jdbc.core.SqlParameter
import java.sql.Types
import javax.sql.DataSource

class InsertSingerAlbum(dataSource: DataSource): BatchSqlUpdate(dataSource, SQL_INSERT_SINGER_ALBUM) {

    init {
        declareParameter(SqlParameter("singer_id", Types.INTEGER))
        declareParameter(SqlParameter("title", Types.VARCHAR))
        declareParameter(SqlParameter("release_date", Types.DATE))

        setBatchSize(BATCH_SIZE)
    }

    companion object {

        private const val SQL_INSERT_SINGER_ALBUM =
            "insert into album (singer_id, title, release_date) values (:singer_id, :title, :release_date)"

        private const val BATCH_SIZE = 10
    }
}