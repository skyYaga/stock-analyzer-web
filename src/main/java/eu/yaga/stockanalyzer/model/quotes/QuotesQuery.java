package eu.yaga.stockanalyzer.model.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representation of a YQL query JSON response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotesQuery {

    private QuotesResults results;

    public QuotesResults getResults() {
        return results;
    }

    public void setResults(QuotesResults results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Query{" +
                "results=" + results +
                '}';
    }
}
