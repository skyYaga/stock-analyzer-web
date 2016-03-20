package eu.yaga.stockanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Representation of a YQL query JSON response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Results {

    private List<ExchangeRate> quote;

    public List<ExchangeRate> getQuote() {
        return quote;
    }

    public void setQuote(List<ExchangeRate> quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "Results{" +
                "quote=" + quote +
                '}';
    }
}
