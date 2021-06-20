package com.mthaler.springjdbc.config

import org.apache.commons.dbcp2.BasicDataSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import javax.sql.DataSource

@Configuration
@PropertySource("classpath:db/jdbc2.properties")
@ComponentScan(basePackages = ["com.mthaler.springjdbc"])
class AppConfig {
    @Value("\${driverClassName}")
    private val driverClassName: String? = null

    @Value("\${url}")
    private val url: String? = null

    @Value("\${username}")
    private val username: String? = null

    @Value("\${password}")
    private val password: String? = null
    @Bean(destroyMethod = "close")
    fun dataSource(): DataSource {
        return try {
            val dataSource = BasicDataSource()
            dataSource.setDriverClassName(driverClassName)
            dataSource.setUrl(url)
            dataSource.setUsername(username)
            dataSource.setPassword(password)
            dataSource
        } catch (e: Exception) {
            logger.error("DBCP DataSource bean cannot be created!", e)
            throw e
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AppConfig::class.java)

        @Bean
        fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer {
            return PropertySourcesPlaceholderConfigurer()
        }
    }
}