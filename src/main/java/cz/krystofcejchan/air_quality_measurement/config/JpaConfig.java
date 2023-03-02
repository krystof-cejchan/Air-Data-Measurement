package cz.krystofcejchan.air_quality_measurement.config;

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
    /**
     * Localhost JPA conf
     *
     * @return DataSource for localhost mysql db
     */
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
     * Server mysql db
     *
     * @return DataSource for server-side db
     */
   /* @Primary
    @Bean
    public DataSource getPrimaryDataSource() {
        DataSourceBuilder<? extends DataSource> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(System.getenv("DB"));
        dataSourceBuilder.username("doadmin");
        dataSourceBuilder.password(cz.krystofcejchan.air_quality_measurement.AqmApplication.dbpsd);
        return dataSourceBuilder.build();
    }
*/
    @Primary
    @Bean
    public DataSource getPrimaryDataSource() {
        DataSourceBuilder<? extends DataSource> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("");
        dataSourceBuilder.username("");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }
}
