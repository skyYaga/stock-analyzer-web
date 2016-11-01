package eu.yaga.stockanalyzer.controller.api;

import eu.yaga.stockanalyzer.model.FundamentalData;
import eu.yaga.stockanalyzer.repository.FundamentalDataRepository;
import eu.yaga.stockanalyzer.service.FundamentalDataService;
import eu.yaga.stockanalyzer.service.StockRatingBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * REST Controller for fundamental data
 */
@RestController
@RequestMapping("/api/fundamental-data")
class FundamentalDataController {

    private static final Logger log = LoggerFactory.getLogger(FundamentalDataController.class);

    @Autowired
    private FundamentalDataService fundamentalDataService;

    @Autowired
    private StockRatingBusinessService stockRatingBusinessService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FundamentalDataRepository fundamentalDataRepository;

    private final AtomicLong counter = new AtomicLong();


    /**
     * This Controller returns all symbols with cached fundamental data<br/>
     *
     * @return stock symbols
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public HashSet<String> getCachedSymbols() throws ParseException {
        Query query = new Query();
        query.fields().include("symbol");
        List<FundamentalData> fundamentalDataList = mongoTemplate.find(query, FundamentalData.class);
        HashSet<String> symbols = new HashSet<>();
        for (FundamentalData data : fundamentalDataList) {
            symbols.add(data.getSymbol());
        }
        return symbols;
    }

    /**
     * This Controller returns the fundamental data for the given symbol<br/>
     *
     * @param symbol the stocks symbol
     * @return the fundamental data
     */
    @RequestMapping(value = "/{symbol:.+}", method = RequestMethod.GET)
    public FundamentalData getFundamentalData(@PathVariable String symbol) throws ParseException {
        return fundamentalDataRepository.findBySymbolOrderByDateDesc(symbol);
    }

    /**
     * This Controller refreshes the fundamental data for the given symbol<br/>
     *
     * @param symbol the stocks symbol
     * @return the fundamental data
     */
    @RequestMapping(value = "/{symbol}/refresh", method = RequestMethod.GET)
    public FundamentalData refreshFundamentalData(@PathVariable String symbol) throws ParseException {
        FundamentalData fundamentalData = fundamentalDataService.getFundamentalData(symbol);
        log.info("Got Fundamental Data: " + fundamentalData);

        fundamentalData = stockRatingBusinessService.rate(fundamentalData);
        log.info("Fundamental Data rated: " + fundamentalData);

        FundamentalData saved = fundamentalDataRepository.save(fundamentalData);
        log.info("SAVED: " + saved);

        return fundamentalData;
    }
}
