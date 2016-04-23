package eu.yaga.stockanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * A POJO for fundamental data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundamentalData {

    private ArrayList<String> businessYears;
    private double roe;
    private double ebit;
    private double equityRatio;
    private double ask;
    private double perCurrent;
    private double per5years;

    /**
     * get the business years of the share
     * @return
     */
    public ArrayList<String> getBusinessYears() {
        return businessYears;
    }

    /**
     * set the business years of the share
     * @param businessYears
     */
    public void setBusinessYears(ArrayList<String> businessYears) {
        this.businessYears = businessYears;
    }

    /**
     * get the return on equity (Eigenkapitalrendite) of the share in percentage
     * @return the roe
     */
    public double getRoe() {
        return roe;
    }

    /**
     * set the return on equity (Eigenkapitalrendite) of the share in percentage
     * @param roe
     */
    public void setRoe(double roe) {
        this.roe = roe;
    }

    /**
     * get the ebit (EBIT-Marge) of the share in percentage
     * @return
     */
    public double getEbit() {
        return ebit;
    }

    /**
     * set the ebit (EBIT-Marge) of the share in percentage
     * @param ebit
     */
    public void setEbit(double ebit) {
        this.ebit = ebit;
    }

    /**
     * get the equity ratio (Eigenkapitalquote) of the share in percentage
     * @return
     */
    public double getEquityRatio() {
        return equityRatio;
    }

    /**
     * set the equity ratio (Eigenkapitalquote) of the share in percentage
     * @param equityRatio
     */
    public void setEquityRatio(double equityRatio) {
        this.equityRatio = equityRatio;
    }

    /**
     * get the current ask price of the share in percentage
     * @return
     */
    public double getAsk() {
        return ask;
    }

    /**
     * set the current ask price of the share in percentage
     * @param ask
     */
    public void setAsk(double ask) {
        this.ask = ask;
    }

    /**
     * get the current price-earning ratio PER (KGV)
     * @return
     */
    public double getPerCurrent() {
        return perCurrent;
    }

    /**
     * set the price-earning ratio PER (KGV)
     * @param perCurrent
     */
    public void setPerCurrent(double perCurrent) {
        this.perCurrent = perCurrent;
    }

    /**
     * get the 5 years price-earning ratio PER (KGV)
     * @return
     */
    public double getPer5years() {
        return per5years;
    }

    /**
     * set the 5 years price-earning ratio PER (KGV)
     * @param per5years
     */
    public void setPer5years(double per5years) {
        this.per5years = per5years;
    }

    /**
     * get the String value of the next year
     * @return year
     */
    public String getNextYear() {
        return businessYears.get(0);
    }

    /**
     * get the String value of the current year
     * @return year
     */
    public String getCurrentYear() {
        return businessYears.get(1);
    }

    /**
     * get the String value of the last year
     * @return year
     */
    public String getLastYear() {
        return businessYears.get(2);
    }

    /**
     * get the String value of two years ago
     * @return year
     */
    public String getTwoYearsAgo() {
        return businessYears.get(3);
    }

    /**
     * get the String value of three years ago
     * @return year
     */
    public String getThreeYearsAgo() {
        return businessYears.get(4);
    }
}
