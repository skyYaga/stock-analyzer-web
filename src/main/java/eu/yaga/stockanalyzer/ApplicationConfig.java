package eu.yaga.stockanalyzer;

import eu.yaga.stockanalyzer.parser.OnVistaParser;
import eu.yaga.stockanalyzer.service.CurrentStockQuotesService;
import eu.yaga.stockanalyzer.service.FundamentalDataService;
import eu.yaga.stockanalyzer.service.HistoricalExchangeRateService;
import eu.yaga.stockanalyzer.service.impl.YahooCurrentStockQuotesServiceImpl;
import eu.yaga.stockanalyzer.service.impl.YahooHistoricalExchangeRateServiceImpl;
import eu.yaga.stockanalyzer.service.impl.OnVistaFundamentalDataServiceImpl;
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
        return new YahooHistoricalExchangeRateServiceImpl();
    }

    @Bean
    public FundamentalDataService getFundamentalDataService() {
        return new OnVistaFundamentalDataServiceImpl();
    }

    @Bean
    public CurrentStockQuotesService getCurrentStockQuotesService() {
        return new YahooCurrentStockQuotesServiceImpl();
    }

    @Bean
    public OnVistaParser getOnVistaParser() {
        return new OnVistaParser();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
