package com.mthaler.springjdbc

import org.springframework.jdbc.`object`.SqlFunction
import org.springframework.jdbc.core.SqlParameter
import java.sql.Types
import javax.sql.DataSource

class StoredFunctionFirstNameById(dataSource: DataSource): SqlFunction<String>(dataSource, SQL) {

    init {
        declareParameter(SqlParameter(Types.INTEGER))
        compile()
    }

    companion object {

        private const val SQL = "select getfirstnamebyid(?)"
    }
}