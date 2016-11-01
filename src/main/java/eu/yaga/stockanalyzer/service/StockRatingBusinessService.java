package eu.yaga.stockanalyzer.service;

import eu.yaga.stockanalyzer.model.FundamentalData;
import org.springframework.stereotype.Service;

/**
 * Service that rates stocks based on their indicators
 */
@Service
public interface StockRatingBusinessService {

    /**
     * rate a stock
     * @param fd the stock
     * @return the FundamentalData with ratings
     */
    FundamentalData rate(FundamentalData fd);

}
