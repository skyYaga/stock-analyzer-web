package eu.yaga.stockanalyzer.model.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representation of a YQL query JSON response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotesResults {

    private QuotesRate quote;

    public QuotesRate getQuote() {
        return quote;
    }

    public void setQuote(QuotesRate quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "Results{" +
                "quote=" + quote +
                '}';
    }
}
