package eu.yaga.stockanalyzer.model.xchange;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representation of a YQL query JSON response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YqlXchangeQuery {
    private ExchangeQuery query;

    public ExchangeQuery getQuery() {
        return query;
    }

    public void setQuery(ExchangeQuery query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "YqlQuery{" +
                "query=" + query +
                '}';
    }
}
