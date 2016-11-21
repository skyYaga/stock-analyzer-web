package eu.yaga.stockanalyzer;

import eu.yaga.stockanalyzer.config.MongoUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application class
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    MongoUserDetailsService userDetailsService;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception { }
}
