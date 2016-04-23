package eu.yaga.stockanalyzer.model.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representation of a YQL query JSON response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YqlQuotesQuery {
    private QuotesQuery query;

    public QuotesQuery getQuery() {
        return query;
    }

    public void setQuery(QuotesQuery query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "YqlQuery{" +
                "query=" + query +
                '}';
    }
}
