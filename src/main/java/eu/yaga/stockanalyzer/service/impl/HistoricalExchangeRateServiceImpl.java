package eu.yaga.stockanalyzer.service.impl;

import eu.yaga.stockanalyzer.model.ExchangeRate;
import eu.yaga.stockanalyzer.model.YqlQuery;
import eu.yaga.stockanalyzer.service.HistoricalExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
        RestTemplate restTemplate = new RestTemplate();

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


        String baseUrl = "https://query.yahooapis.com/v1/public/yql";

        String queryString = "?q=select * from yahoo.finance.historicaldata where symbol = '" + symbol +
                "' and startDate = '" + sdf.format(dateFrom) + "' and endDate = '" + sdf.format(dateTo) + "'";
        String formatString = "&format=json";
        String envString = "&env=store://datatables.org/alltableswithkeys";

        YqlQuery queryResult = restTemplate.getForObject(baseUrl + queryString + formatString + envString, YqlQuery.class);
        log.info(queryResult.toString());

        return queryResult.getQuery().getResults().getQuote();
    }
}
