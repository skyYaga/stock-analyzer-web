package eu.yaga.stockanalyzer.service;

import eu.yaga.stockanalyzer.model.ExchangeRate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * Service handling historical exchange rates
 */
@Service
public interface HistoricalExchangeRateService {

    /**
     * This method returns historical exchange Rates of the given stock
     *
     * @param symbol Symbol of the stock
     * @param dateStringFrom   Date of the start of the historical data (yyyy-MM-dd)
     * @param dateStringTo     Date of the end of the historical data (yyyy-MM-dd)
     * @return Historical Exchange Rates
     */
    List<ExchangeRate> getHistoricalExchangeRates(String symbol, String dateStringFrom, String dateStringTo) throws ParseException;
}
