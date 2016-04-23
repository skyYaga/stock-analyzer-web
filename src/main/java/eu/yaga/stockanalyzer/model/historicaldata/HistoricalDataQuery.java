package eu.yaga.stockanalyzer.model.historicaldata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representation of a YQL query JSON response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalDataQuery {

    private HistoricalDataResults results;

    public HistoricalDataResults getResults() {
        return results;
    }

    public void setResults(HistoricalDataResults results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Query{" +
                "results=" + results +
                '}';
    }
}
