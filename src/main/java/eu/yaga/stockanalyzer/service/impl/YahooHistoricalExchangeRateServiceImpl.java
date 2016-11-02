package eu.yaga.stockanalyzer.service.impl;

import eu.yaga.stockanalyzer.model.FundamentalData;
import eu.yaga.stockanalyzer.model.historicaldata.HistoricalDataQuote;
import eu.yaga.stockanalyzer.model.historicaldata.YqlHistoricalDataQuery;
import eu.yaga.stockanalyzer.service.HistoricalExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Implementation of the {@link HistoricalExchangeRateService}
 */
public class YahooHistoricalExchangeRateServiceImpl implements HistoricalExchangeRateService {

    private static final Logger log = LoggerFactory.getLogger(YahooHistoricalExchangeRateServiceImpl.class);

    static final String YQL_BASE_URL = "https://query.yahooapis.com/v1/public/yql";
    static final String YQL_QUERY_POSTFIX = "&format=json&env=store://datatables.org/alltableswithkeys";
    static final String YQL_QUERY_HISTORICAL_RATES =
            "?q=select * from yahoo.finance.historicaldata where symbol = '%s' and startDate = '%s' and endDate = '%s'";


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private RestTemplate restTemplate;

    /**
     * This method returns historical exchange Rates of the given stock
     *
     * @param symbol Symbol of the stock
     * @param dateStringFrom   Date of the start of the historical data (yyyy-MM-dd)
     * @param dateStringTo     Date of the end of the historical data (yyyy-MM-dd)
     * @return Historical Exchange Rates
     */
    @Override
    public List<HistoricalDataQuote> getHistoricalExchangeRates(String symbol, String dateStringFrom, String dateStringTo) throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();

        // Create default values
        Date dateTo = new Date();
        if (dateStringTo != null) {
            dateTo = sdf.parse(dateStringTo);
        }

        calendar.setTime(dateTo);
        calendar.add(Calendar.YEAR, -1);
        Date dateFrom = calendar.getTime();

        if (dateStringFrom != null) {
            dateFrom = sdf.parse(dateStringFrom);
        }

        if (dateFrom.equals(dateTo)) {
            throw new RuntimeException("The dates may not be equal!");
        }
        if (dateFrom.after(dateTo)) {
            throw new RuntimeException("The from date has to be before the to date!");
        }

        String queryString = String.format(YQL_QUERY_HISTORICAL_RATES, symbol, sdf.format(dateFrom), sdf.format(dateTo));

        YqlHistoricalDataQuery queryResult = new YqlHistoricalDataQuery();
        try {
            queryResult = restTemplate.getForObject(YQL_BASE_URL + queryString + YQL_QUERY_POSTFIX, YqlHistoricalDataQuery.class);
        } catch (Exception e) {
            log.error("YqlHistoricalDataQuery failed: " + e.getLocalizedMessage());
        }
        log.info(queryResult.toString());

        if (queryResult.getQuery() != null
                && queryResult.getQuery().getResults() != null
                && queryResult.getQuery().getResults().getQuote() != null) {
            return queryResult.getQuery().getResults().getQuote();
        }

        return new ArrayList<>();
    }

    /**
     * This method returns the stock's reaction to quarterly figures (comparing it to its index)
     *
     * @param fundamentalData of the stock
     * @return the progress difference to the index
     */
    @Override
    public double getReactionToQuarterlyFigures(FundamentalData fundamentalData) {
        try {
            Date dateLegacy = fundamentalData.getLastQuarterlyFigures();
            String symbol = fundamentalData.getSymbol();
            String indexSymbol = fundamentalData.getStockIndex();

            if (dateLegacy == null || symbol == null || indexSymbol == null) {
                return -9999;
            }

            LocalDate date = dateLegacy.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String dateString = date.format(dtf);
            String priorDay = date.minusDays(1).format(dtf);

            List<HistoricalDataQuote> ratesSymbol = getHistoricalExchangeRates(symbol, priorDay, dateString);
            LocalDate dateTmp = date;
            while (ratesSymbol.size() < 2) {
                dateTmp = dateTmp.minusDays(1);
                priorDay = dateTmp.format(dtf);
                ratesSymbol = getHistoricalExchangeRates(symbol, priorDay, dateString);
            }

            List<HistoricalDataQuote> ratesIndex = getHistoricalExchangeRates(indexSymbol, priorDay, dateString);

            // calculate Data
            double closeSymbol = ratesSymbol.get(0).getClose();
            double closeSymbolPriorDay = ratesSymbol.get(1).getClose();
            double closeIndex = ratesIndex.get(0).getClose();
            double closeIndexPriorDay = ratesIndex.get(1).getClose();

            double progressSymbol = (1 - closeSymbolPriorDay / closeSymbol) * 100;
            log.info("progressSymbol " + symbol + ": " + progressSymbol);
            double progressIndex = (1 - closeIndexPriorDay / closeIndex) * 100;
            log.info("progressIndex " + indexSymbol + ": " + progressIndex);

            double totalProgress = progressSymbol - progressIndex;
            log.info("totalProgress: " + totalProgress);
            return totalProgress;
        } catch (ParseException e) {
            return -9999;
        }
    }

    /**
     * This method calculates the stock progression within the last 6 months
     *
     * @param fundamentalData of the stock
     * @return the progression in percent
     */
    @Override
    public double getRateProgress6month(FundamentalData fundamentalData) {
        return getRateProgress(fundamentalData, ChronoUnit.MONTHS, 6);
    }

    /**
     * This method calculates the stock progression within the last 1 year
     *
     * @param fundamentalData of the stock
     * @return the progression in percent
     */
    @Override
    public double getRateProgress1year(FundamentalData fundamentalData) {
        return getRateProgress(fundamentalData, ChronoUnit.YEARS, 1);
    }

    private double getRateProgress(FundamentalData fundamentalData, ChronoUnit chronoUnit, int amount) {
        try {
            String symbol = fundamentalData.getSymbol();

            // Fetch data
            LocalDate today = LocalDate.now();
            LocalDate todayMinus = today.minusDays(1);

            List<HistoricalDataQuote> ratesToday = getHistoricalExchangeRates(symbol, todayMinus.format(dtf), today.format(dtf));
            while (ratesToday.size() < 1) {
                todayMinus = todayMinus.minusDays(1);
                ratesToday = getHistoricalExchangeRates(symbol, todayMinus.format(dtf), today.format(dtf));
            }

            // Find data for compareDate
            LocalDate compareDate = today.minus(amount, chronoUnit);
            LocalDate compareDateMinus = compareDate.minusDays(1);

            List<HistoricalDataQuote> ratesCompareDate = getHistoricalExchangeRates(symbol, compareDateMinus.format(dtf), compareDate.format(dtf));
            while (ratesCompareDate.size() < 1) {
                compareDateMinus = compareDateMinus.minusDays(1);
                ratesCompareDate = getHistoricalExchangeRates(symbol, compareDateMinus.format(dtf), compareDate.format(dtf));
            }

            double closeToday = ratesToday.get(0).getClose();
            log.info("closeToday: " + closeToday);
            double closeCompareDate = ratesCompareDate.get(0).getClose();
            log.info("closeCompareDate: " + closeCompareDate);

            double rateProgress = (closeToday - closeCompareDate) / closeCompareDate * 100;
            log.info("rateProgress: " + rateProgress);

            return rateProgress;
        } catch (ParseException e) {
            return -9999;
        }
    }
}
