package eu.yaga.stockanalyzer.service.impl;

import eu.yaga.stockanalyzer.model.FundamentalData;
import eu.yaga.stockanalyzer.service.HistoricalExchangeRateService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for StockRatingBusinessServiceImpl
 */
public class StockRatingBusinessServiceImplTest {

    private StockRatingBusinessServiceImpl service;

    public void setUpGood() {

    }

    @Test
    public void testGoodRating() {

        FundamentalData fd = new FundamentalData();
        fd.setRoe(21);
        fd.setEbit(13);
        fd.setEquityRatio(26);
        fd.setPer5years(11);
        fd.setPerCurrent(11);
        fd.setAnalystEstimation(2.6);
        fd.setLastQuarterlyFigures(new Date());
        List<Double> reversalList = new ArrayList<>();
        reversalList.add(1.0);
        reversalList.add(2.0);
        reversalList.add(3.0);
        fd.setReversal3Month(reversalList);

        HistoricalExchangeRateService mockedRateService = mock(HistoricalExchangeRateService.class);
        when(mockedRateService.getReactionToQuarterlyFigures(fd)).thenReturn(1.1);
        when(mockedRateService.getRateProgress6month(fd)).thenReturn(6.0);
        when(mockedRateService.getRateProgress1year(fd)).thenReturn(7.0);
        when(mockedRateService.getReversal3Month(fd)).thenReturn(reversalList);
        service = new StockRatingBusinessServiceImpl(mockedRateService);

        fd = service.rate(fd);

        Assert.assertEquals("OverallRating", 10, fd.getOverallRating());
        Assert.assertEquals("RateProgress6month", 6.0, fd.getRateProgress6month(), 0);
        Assert.assertEquals("RateProgress6monthRating", 1, fd.getRateProgress6monthRating());
        Assert.assertEquals("RateProgress1year", 7.0, fd.getRateProgress1year(), 0);
        Assert.assertEquals("RateProgress1yearRating", 1, fd.getRateProgress1yearRating());
        Assert.assertEquals("Reversal3MonthRating", 1, fd.getReversal3MonthRating());
        Assert.assertEquals("RateMomentumRating", 0, fd.getRateMomentumRating());
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
        fd.setLastQuarterlyFigures(new Date());
        List<Double> reversalList = new ArrayList<>();
        reversalList.add(-1.0);
        reversalList.add(-2.0);
        reversalList.add(3.0);
        fd.setReversal3Month(reversalList);

        HistoricalExchangeRateService mockedRateService = mock(HistoricalExchangeRateService.class);
        when(mockedRateService.getReactionToQuarterlyFigures(fd)).thenReturn(0.0);
        when(mockedRateService.getRateProgress6month(fd)).thenReturn(2.0);
        when(mockedRateService.getRateProgress1year(fd)).thenReturn(-2.0);
        when(mockedRateService.getReversal3Month(fd)).thenReturn(reversalList);
        service = new StockRatingBusinessServiceImpl(mockedRateService);

        fd = service.rate(fd);

        Assert.assertEquals("roeRating", 0, fd.getRoeRating());
        Assert.assertEquals("ebitRating", 0, fd.getEbitRating());
        Assert.assertEquals("equityRatioRating", 0, fd.getEquityRatioRating());
        Assert.assertEquals("Per5yearsRating", 0, fd.getPer5yearsRating());
        Assert.assertEquals("PerCurrentRating", 0, fd.getPerCurrentRating());
        Assert.assertEquals("AnalystEstimationRating", 0, fd.getAnalystEstimationRating());
        Assert.assertEquals("LastQuarterlyFiguresRating", 0, fd.getLastQuarterlyFiguresRating());
        Assert.assertEquals("RateProgress6month", 2.0, fd.getRateProgress6month(), 0);
        Assert.assertEquals("RateProgress6monthRating", 0, fd.getRateProgress6monthRating());
        Assert.assertEquals("RateProgress1year", -2.0, fd.getRateProgress1year(), 0);
        Assert.assertEquals("RateProgress1yearRating", 0, fd.getRateProgress1yearRating());
        Assert.assertEquals("RateMomentumRating", 0, fd.getRateMomentumRating());
        Assert.assertEquals("Reversal3MonthRating", 0, fd.getReversal3MonthRating());
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
        fd.setLastQuarterlyFigures(new Date());
        List<Double> reversalList = new ArrayList<>();
        reversalList.add(-1.0);
        reversalList.add(-2.0);
        reversalList.add(-3.0);
        fd.setReversal3Month(reversalList);

        HistoricalExchangeRateService mockedRateService = mock(HistoricalExchangeRateService.class);
        when(mockedRateService.getReactionToQuarterlyFigures(fd)).thenReturn(-1.1);
        when(mockedRateService.getRateProgress6month(fd)).thenReturn(-6.0);
        when(mockedRateService.getRateProgress1year(fd)).thenReturn(-7.0);
        when(mockedRateService.getReversal3Month(fd)).thenReturn(reversalList);
        service = new StockRatingBusinessServiceImpl(mockedRateService);

        fd = service.rate(fd);

        Assert.assertEquals("RateProgress6month", -6.0, fd.getRateProgress6month(), 0);
        Assert.assertEquals("RateProgress6monthRating", -1, fd.getRateProgress6monthRating());
        Assert.assertEquals("RateProgress1year", -7.0, fd.getRateProgress1year(), 0);
        Assert.assertEquals("RateProgress1yearRating", -1, fd.getRateProgress1yearRating());
        Assert.assertEquals("RateMomentumRating", 0, fd.getRateMomentumRating());
        Assert.assertEquals("Reversal3MonthRating", -1, fd.getReversal3MonthRating());
        Assert.assertEquals("OverallRating", -10, fd.getOverallRating());
    }

    @Test
    public void testGoodRateMomentumRating() {
        FundamentalData fd = new FundamentalData();

        HistoricalExchangeRateService mockedRateService = mock(HistoricalExchangeRateService.class);
        when(mockedRateService.getRateProgress6month(fd)).thenReturn(6.0);
        when(mockedRateService.getRateProgress1year(fd)).thenReturn(0.0);
        service = new StockRatingBusinessServiceImpl(mockedRateService);

        fd = service.rate(fd);

        Assert.assertEquals("RateMomentumRating", 1, fd.getRateMomentumRating());
    }

    @Test
    public void testBadRateMomentumRating() {
        FundamentalData fd = new FundamentalData();

        HistoricalExchangeRateService mockedRateService = mock(HistoricalExchangeRateService.class);
        when(mockedRateService.getRateProgress6month(fd)).thenReturn(-6.0);
        when(mockedRateService.getRateProgress1year(fd)).thenReturn(0.0);
        service = new StockRatingBusinessServiceImpl(mockedRateService);

        fd = service.rate(fd);

        Assert.assertEquals("RateMomentumRating", -1, fd.getRateMomentumRating());
    }
}
