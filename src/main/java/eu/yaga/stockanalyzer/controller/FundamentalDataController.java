package eu.yaga.stockanalyzer.controller;

import eu.yaga.stockanalyzer.model.FundamentalData;
import eu.yaga.stockanalyzer.service.FundamentalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * REST Controller for fundamental data
 */
@RestController
@RequestMapping("/fundamental-data")
class FundamentalDataController {

    @Autowired
    private FundamentalDataService fundamentalDataService;

    private final AtomicLong counter = new AtomicLong();

    /**
     * This Controller returns the fundamental data for the given symbol<br/>
     * The date parameters are optional
     *
     * @param symbol the stocks symbol
     * @return the fundamental data
     */
    @RequestMapping(value = "/{symbol}", method = RequestMethod.GET)
    public FundamentalData getFundamentalData(@PathVariable String symbol) throws ParseException {
        return fundamentalDataService.getFundamentalData(symbol);
    }
}
