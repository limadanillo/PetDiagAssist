package br.com.petdiagassist.config;

import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Flyway configuration class
 *
 * @author Danillo Lima
 * @since 06/10/2021
 */
@Configuration
@Log4j2
public class FlywayConfig {

    private final Environment env;

    public FlywayConfig(final Environment env) {
        this.env = env;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        var flyway =
                new Flyway(
                        Flyway.configure()
                                .baselineOnMigrate(false)
                                .dataSource(
                                        env.getRequiredProperty("spring.flyway.url"),
                                        env.getRequiredProperty("spring.flyway.user"),
                                        env.getRequiredProperty("spring.flyway.password")));

        log.info("FLYWAY LOCATIONS IS {}", flyway.getConfiguration().getLocations()[0].getPath());
        return flyway;
    }
}