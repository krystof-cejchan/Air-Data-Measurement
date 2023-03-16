package cz.krystofcejchan.air_quality_measurement.config;

import cz.krystofcejchan.air_quality_measurement.AqmApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * JPA configuration
 */
@Configuration
public class JpaConfig {

    /*@Primary
    @Bean
    public DataSource getSecondaryDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/airDataMeasurement");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("jetotereza");
        return dataSourceBuilder.build();
    }*/

    /**
     * MySQL server connection bean
     *
     * @return DataSource
     */
    @Primary
    @Bean
    public DataSource getPrimaryDataSource() {
        DataSourceBuilder<? extends DataSource> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(System.getenv("DB"));
        dataSourceBuilder.username("doadmin");
        dataSourceBuilder.password(AqmApplication.dbpsd);
        return dataSourceBuilder.build();
    }
}
