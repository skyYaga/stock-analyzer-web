package eu.yaga.stockanalyzer.model.xchange;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representation of a YQL query JSON response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeResults {

    private ExchangeRate rate;

    public ExchangeRate getRate() {
        return rate;
    }

    public void setRate(ExchangeRate quote) {
        this.rate = quote;
    }

    @Override
    public String toString() {
        return "Results{" +
                "rate=" + rate +
                '}';
    }
}
