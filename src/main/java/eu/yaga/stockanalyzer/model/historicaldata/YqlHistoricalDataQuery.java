package eu.yaga.stockanalyzer.model.historicaldata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representation of a YQL query JSON response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YqlHistoricalDataQuery {
    private HistoricalDataQuery query;

    public HistoricalDataQuery getQuery() {
        return query;
    }

    public void setQuery(HistoricalDataQuery query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "YqlQuery{" +
                "query=" + query +
                '}';
    }
}
