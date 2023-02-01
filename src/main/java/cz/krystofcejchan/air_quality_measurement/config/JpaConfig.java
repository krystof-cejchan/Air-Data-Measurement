package cz.krystofcejchan.air_quality_measurement.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    @Primary
    @Bean
    public DataSource getSecondaryDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/airDataMeasurement");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("jetotereza");
        return dataSourceBuilder.build();
    }
/*
    @Primary
    @Bean
    public DataSource getPrimaryDataSource() {
        DataSourceBuilder<? extends DataSource> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://doadmin:AVNS_MrIOZIhEXx3CUdn6f0k@db-mysql-fra1-97613-do-user-13223853-0.b.db.ondigitalocean.com:25060/airdatameasurement?ssl-mode=REQUIRED");
        dataSourceBuilder.username("doadmin");
        dataSourceBuilder.password(cz.krystofcejchan.air_quality_measurement.AqmApplication.dbpsd);
        return dataSourceBuilder.build();
    }*/
}
