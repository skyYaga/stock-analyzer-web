package eu.yaga.stockanalyzer.service;

import eu.yaga.stockanalyzer.model.FundamentalData;

/**
 * Service that rates stocks based on their indicators
 */
public interface StockRatingBusinessService {

    /**
     * rate a stock
     * @param fundamentalData the stock
     * @return the FundamentalData with ratings
     */
    FundamentalData rate(FundamentalData fundamentalData);

}
