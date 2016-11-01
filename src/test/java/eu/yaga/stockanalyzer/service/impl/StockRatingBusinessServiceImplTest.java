package eu.yaga.stockanalyzer.service.impl;

import eu.yaga.stockanalyzer.model.FundamentalData;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for StockRatingBusinessServiceImpl
 */
public class StockRatingBusinessServiceImplTest {

    private StockRatingBusinessServiceImpl service = new StockRatingBusinessServiceImpl();

    @Test
    public void testGoodRating() {
        FundamentalData fd = new FundamentalData();
        fd.setRoe(21);
        fd.setEbit(13);
        fd.setEquityRatio(26);
        fd.setPer5years(11);
        fd.setPerCurrent(11);
        fd.setAnalystEstimation(2.6);

        fd = service.rate(fd);

        Assert.assertEquals("OverallRating", 6, fd.getOverallRating());
    }

    @Test
    public void testNeutralRating() {
        FundamentalData fd = new FundamentalData();
        fd.setRoe(15);
        fd.setEbit(9);
        fd.setEquityRatio(20);
        fd.setPer5years(14);
        fd.setPerCurrent(14);
        fd.setAnalystEstimation(2);

        fd = service.rate(fd);

        Assert.assertEquals("roeRating", 0, fd.getRoeRating());
        Assert.assertEquals("ebitRating", 0, fd.getEbitRating());
        Assert.assertEquals("equityRatioRating", 0, fd.getEquityRatioRating());
        Assert.assertEquals("Per5yearsRating", 0, fd.getPer5yearsRating());
        Assert.assertEquals("PerCurrentRating", 0, fd.getPerCurrentRating());
        Assert.assertEquals("AnalystEstimationRating", 0, fd.getAnalystEstimationRating());
        Assert.assertEquals("OverallRating", 0, fd.getOverallRating());
    }

    @Test
    public void testBadRating() {
        FundamentalData fd = new FundamentalData();
        fd.setRoe(9);
        fd.setEbit(5);
        fd.setEquityRatio(14);
        fd.setPer5years(17);
        fd.setPerCurrent(17);
        fd.setAnalystEstimation(1.5);

        fd = service.rate(fd);

        Assert.assertEquals("OverallRating", -6, fd.getOverallRating());
    }
}
