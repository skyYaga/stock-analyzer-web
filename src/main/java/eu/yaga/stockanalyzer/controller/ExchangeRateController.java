package eu.yaga.stockanalyzer.controller;

import eu.yaga.stockanalyzer.model.ExchangeRate;
import eu.yaga.stockanalyzer.service.HistoricalExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * REST Controller for exchange rates
 */
@RestController
@RequestMapping("/exchange-rate")
class ExchangeRateController {

    @Autowired
    private HistoricalExchangeRateService historicalExchangeRateService;

    private final AtomicLong counter = new AtomicLong();

    /**
     * This Controller returns the historical exchange rates for the given symbol<br/>
     * The date parameters are optional
     *
     * @param symbol the stocks symbol
     * @param dateStringFrom Date of the start of the historical data (yyyy-MM-dd)
     * @param dateStringTo Date of the end of the historical data (yyyy-MM-dd)
     * @return a list of historical exchange rates
     * @throws ParseException if a submitted date is invalid
     */
    @RequestMapping(value = "/{symbol}", method = RequestMethod.GET)
    public List<ExchangeRate> getExchangeRateForRange(
            @PathVariable String symbol,
            @RequestParam(value = "from", required = false) String dateStringFrom,
            @RequestParam(value = "to", required = false) String dateStringTo
    ) throws ParseException {
        return historicalExchangeRateService.getHistoricalExchangeRates(symbol, dateStringFrom, dateStringTo);
    }
}
