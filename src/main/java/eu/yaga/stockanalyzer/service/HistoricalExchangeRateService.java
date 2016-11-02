package eu.yaga.stockanalyzer.service;

import eu.yaga.stockanalyzer.model.FundamentalData;
import eu.yaga.stockanalyzer.model.historicaldata.HistoricalDataQuote;
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
    List<HistoricalDataQuote> getHistoricalExchangeRates(String symbol, String dateStringFrom, String dateStringTo) throws ParseException;

    /**
     * This method returns the stock's reaction to quarterly figures (comparing it to its index)
     *
     * @param fundamentalData of the stock
     * @return the progress difference to the index
     */
    double getReactionToQuarterlyFigures(FundamentalData fundamentalData);

    /**
     * This method calculates the stock progression within the last 6 months
     *
     * @param fundamentalData of the stock
     * @return the progression in percent
     */
    double getRateProgress6month(FundamentalData fundamentalData);

    /**
     * This method calculates the stock progression within the last 1 year
     * @param fundamentalData of the stock
     * @return the progression in percent
     */
    double getRateProgress1year(FundamentalData fundamentalData);
}
