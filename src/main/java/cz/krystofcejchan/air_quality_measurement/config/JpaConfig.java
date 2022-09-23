package cz.krystofcejchan.air_quality_measurement.config;

import cz.krystofcejchan.air_quality_measurement.AqmApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    @Bean
    @Primary
    public DataSource getPrimaryDataSource()
    {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://sql11.freemysqlhosting.net/sql11521723");
        dataSourceBuilder.username("sql11521723");
        dataSourceBuilder.password(AqmApplication.dbpsd);
        return dataSourceBuilder.build();
    }
    @Bean
    public DataSource getSecondaryDataSource()
    {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/AirQuality");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }
}