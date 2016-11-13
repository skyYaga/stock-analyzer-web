package eu.yaga.stockanalyzer.model;

import eu.yaga.stockanalyzer.model.historicaldata.HistoricalDataQuote;

/**
 * Created by andreas on 13.11.16.
 */
public class RateProgressBean {

    private HistoricalDataQuote baseDateQuote;
    private HistoricalDataQuote compareDateQuote;
    private double progress;

    public RateProgressBean(HistoricalDataQuote baseDateQuote, HistoricalDataQuote compareDateQuote, double progress) {
        this.baseDateQuote = baseDateQuote;
        this.compareDateQuote = compareDateQuote;
        this.progress = progress;
    }

    public HistoricalDataQuote getBaseDateQuote() {
        return baseDateQuote;
    }

    public void setBaseDateQuote(HistoricalDataQuote baseDateQuote) {
        this.baseDateQuote = baseDateQuote;
    }

    public HistoricalDataQuote getCompareDateQuote() {
        return compareDateQuote;
    }

    public void setCompareDateQuote(HistoricalDataQuote compareDateQuote) {
        this.compareDateQuote = compareDateQuote;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
