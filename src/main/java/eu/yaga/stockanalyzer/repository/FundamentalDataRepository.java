package eu.yaga.stockanalyzer.repository;

import eu.yaga.stockanalyzer.model.FundamentalData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * the Mongo repository that stores fetched and rated data
 */
public interface FundamentalDataRepository extends MongoRepository<FundamentalData, String> {
    FundamentalData findBySymbolOrderByDateDesc(String symbol);
}
