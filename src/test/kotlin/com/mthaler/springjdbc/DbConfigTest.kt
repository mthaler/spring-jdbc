package com.mthaler.springjdbc

import com.mthaler.springjdbc.config.DbConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.GenericXmlApplicationContext
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import javax.sql.DataSource

class DbConfigTest {

    @Test
    @Throws(SQLException::class)
    fun testOne() {
        val ctx = GenericXmlApplicationContext()
        //ctx.load("classpath:spring/drivermanager-cfg-01.xml");
        //ctx.load("classpath:spring/drivermanager-cfg-02.xml");
        ctx.load("classpath:spring/datasource-dbcp.xml")
        ctx.refresh()
        val dataSource: DataSource = ctx.getBean("dataSource", DataSource::class.java)
        assertNotNull(dataSource)
        testDataSource(dataSource)
        ctx.close()
    }

    @Test
    @Throws(SQLException::class)
    fun testTwo() {
        val ctx: GenericApplicationContext = AnnotationConfigApplicationContext(DbConfig::class.java)
        val dataSource: DataSource = ctx.getBean("dataSource", DataSource::class.java)
        assertNotNull(dataSource)
        testDataSource(dataSource)
        ctx.close()
    }

    @Throws(SQLException::class)
    private fun testDataSource(dataSource: DataSource) {
        var connection: Connection? = null
        try {
            connection = dataSource.getConnection()
            val statement: PreparedStatement = connection.prepareStatement("SELECT 1")
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                val mockVal = resultSet.getInt("1")
                assertTrue(mockVal == 1)
            }
            statement.close()
        } catch (e: Exception) {
            logger.debug("Something unexpected happened.", e)
        } finally {
            if (connection != null) {
                connection.close()
            }
        }
    }

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(DbConfigTest::class.java)
    }
}