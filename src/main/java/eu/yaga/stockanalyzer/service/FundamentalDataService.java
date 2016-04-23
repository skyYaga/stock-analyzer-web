package eu.yaga.stockanalyzer.service;

import eu.yaga.stockanalyzer.model.FundamentalData;
import org.springframework.stereotype.Service;

/**
 * Service handling fundamental data
 */
@Service
public interface FundamentalDataService {

    /**
     * This method returns fundamental data of the given stock
     *
     * @param symbol Symbol of the stock
     * @return fundamental data
     */
    FundamentalData getFundamentalData(String symbol);

}
