package cz.krystofcejchan.air_quality_measurement.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    /* @Bean
     @Primary
     public DataSource getPrimaryDataSource() {
         DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
         dataSourceBuilder.url("jdbc:mysql://sql11.freemysqlhosting.net/sql11521723");
         dataSourceBuilder.username("sql11521723");
         dataSourceBuilder.password(AqmApplication.dbpsd);
         return dataSourceBuilder.build();
     }*/

    @Primary
    @Bean
    public DataSource getSecondaryDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/airDataMeasurement");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("jetotereza");
        return dataSourceBuilder.build();
    }

    /*@Primary
    @Bean
    public DataSource getPrimaryDataSource() {
        DataSourceBuilder<? extends DataSource> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://sql11.freemysqlhosting.net/sql11521723");
        dataSourceBuilder.username("sql11521723");
        dataSourceBuilder.password(AqmApplication.dbpsd);
        return dataSourceBuilder.build();
    }*/
}
