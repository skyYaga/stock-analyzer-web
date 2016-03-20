package eu.yaga.stockanalyzer;

import eu.yaga.stockanalyzer.service.HistoricalExchangeRateService;
import eu.yaga.stockanalyzer.service.impl.HistoricalExchangeRateServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Application Config
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public HistoricalExchangeRateService getHistoricalExchangeRateService() {
        return new HistoricalExchangeRateServiceImpl();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
