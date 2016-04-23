package eu.yaga.stockanalyzer.model.xchange;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representation of a YQL query JSON response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeQuery {

    private ExchangeResults results;

    public ExchangeResults getResults() {
        return results;
    }

    public void setResults(ExchangeResults results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Query{" +
                "results=" + results +
                '}';
    }
}
