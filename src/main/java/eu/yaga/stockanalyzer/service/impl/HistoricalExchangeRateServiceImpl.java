package eu.yaga.stockanalyzer.service.impl;

import eu.yaga.stockanalyzer.model.ExchangeRate;
import eu.yaga.stockanalyzer.model.YqlQuery;
import eu.yaga.stockanalyzer.service.HistoricalExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Implementation of the {@link HistoricalExchangeRateService}
 */
public class HistoricalExchangeRateServiceImpl implements HistoricalExchangeRateService {

    private static final Logger log = LoggerFactory.getLogger(HistoricalExchangeRateServiceImpl.class);

    static final String YQL_BASE_URL = "https://query.yahooapis.com/v1/public/yql";
    static final String YQL_QUERY_POSTFIX = "&format=json&env=store://datatables.org/alltableswithkeys";
    static final String YQL_QUERY_HISTORICAL_RATES =
            "?q=select * from yahoo.finance.historicaldata where symbol = '%s' and startDate = '%s' and endDate = '%s'";


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
    public List<ExchangeRate> getHistoricalExchangeRates(String symbol, String dateStringFrom, String dateStringTo) throws ParseException {

        // Create default values
        Date dateTo = new Date();
        if (dateStringTo != null) {
            dateTo = sdf.parse(dateStringTo);
        }

        Calendar calendar = GregorianCalendar.getInstance();
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

        YqlQuery queryResult = restTemplate.getForObject(YQL_BASE_URL + queryString + YQL_QUERY_POSTFIX, YqlQuery.class);
        log.info(queryResult.toString());

        return queryResult.getQuery().getResults().getQuote();
    }
}
