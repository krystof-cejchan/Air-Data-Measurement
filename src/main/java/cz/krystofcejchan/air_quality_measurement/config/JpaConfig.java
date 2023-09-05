package cz.krystofcejchan.air_quality_measurement.config;

import cz.krystofcejchan.air_quality_measurement.enums.Production;
import cz.krystofcejchan.air_quality_measurement.utilities.psw.Psw;
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
     * MySQL server connection bean
     *
     * @return DataSource
     */
    @Primary
    @Bean
    public DataSource getPrimaryDataSource() {
        boolean isProd = Production.valueOf(System.getenv("PROD")).equals(Production.LIVE);

        if (isProd)
            return DataSourceBuilder.create()
                    .url(System.getenv("DB"))
                    .username(System.getenv("DB_U"))
                    .password(String.valueOf(Psw.dbpsd)).build();

        else return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/airDataMeasurement")
                .username("root")
                .password("jetotereza").build();
    }
}
