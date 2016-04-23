package eu.yaga.stockanalyzer.service;

import org.springframework.stereotype.Service;

/**
 * Service handling stock quotes
 */
@Service
public interface CurrentStockQuotesService {

    /**
     * This method returns the current rate of the given stock
     *
     * @param symbol Symbol of the stock
     * @return rate
     */
    double getCurrentRate(String symbol);
}
