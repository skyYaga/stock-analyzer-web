package eu.yaga.stockanalyzer.model.historicaldata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Representation of a YQL query JSON response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalDataResults {

    private List<HistoricalDataQuote> quote;

    public List<HistoricalDataQuote> getQuote() {
        return quote;
    }

    public void setQuote(List<HistoricalDataQuote> quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "Results{" +
                "quote=" + quote +
                '}';
    }
}
