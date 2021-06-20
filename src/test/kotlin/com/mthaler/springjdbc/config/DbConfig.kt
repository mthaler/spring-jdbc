package com.mthaler.springjdbc.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.jdbc.datasource.SimpleDriverDataSource
import java.sql.Driver
import javax.sql.DataSource
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:db/jdbc2.properties")
class DbConfig {

    @Value("\${driverClassName}")
    private val driverClassName: String? = null

    @Value("\${url}")
    private val url: String? = null

    @Value("\${username}")
    private val username: String? = null

    @Value("\${password}")
    private val password: String? = null

    @Lazy
    @Bean
    fun dataSource(): DataSource {
        val dataSource = SimpleDriverDataSource()
        val driver: Class<out Driver> = Class.forName(driverClassName) as Class<out Driver>
        dataSource.setDriverClass(driver)
        dataSource.url = url
        dataSource.username = username
        dataSource.password = password
        return dataSource
    }

    companion object {

        @Bean
        @JvmStatic
        fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer? {
            return PropertySourcesPlaceholderConfigurer()
        }
    }
}