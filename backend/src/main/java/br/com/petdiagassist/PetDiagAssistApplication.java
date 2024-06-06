package br.com.petdiagassist;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class main
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Log4j2
@SpringBootApplication
public class PetDiagAssistApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetDiagAssistApplication.class, args);
        log.info("🚀 Server ready at http://localhost:9090/api");
    }
}
