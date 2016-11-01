package eu.yaga.stockanalyzer.service.impl;

import eu.yaga.stockanalyzer.model.FundamentalData;
import eu.yaga.stockanalyzer.service.HistoricalExchangeRateService;
import eu.yaga.stockanalyzer.service.StockRatingBusinessService;

/**
 * Implementation of StockRatingBusinessService
 */
public class StockRatingBusinessServiceImpl implements StockRatingBusinessService {

    private HistoricalExchangeRateService historicalExchangeRateService;

    public StockRatingBusinessServiceImpl(HistoricalExchangeRateService historicalExchangeRateService) {
        this.historicalExchangeRateService = historicalExchangeRateService;
    }

    /**
     * rate a stock
     *
     * @param fd the stock
     * @return the FundamentalData with ratings
     */
    @Override
    public FundamentalData rate(FundamentalData fd) {
        fd = rateRoe(fd);
        fd = rateEbit(fd);
        fd = rateEquityRatio(fd);
        fd = ratePer5years(fd);
        fd = ratePerCurrent(fd);
        fd = rateAnalystEstimation(fd);
        fd = rateQuarterlyFigures(fd);

        fd = rateOverall(fd);

        return fd;
    }

    private FundamentalData rateQuarterlyFigures(FundamentalData fd) {
        double reactionToQuarterlyFigures = historicalExchangeRateService.getReactionToQuarterlyFigures(fd);

        if (reactionToQuarterlyFigures >= 1) {
            fd.setLastQuarterlyFiguresRating(1);
        } else if (reactionToQuarterlyFigures <= -1) {
            fd.setLastQuarterlyFiguresRating(-1);
        } else {
            fd.setLastQuarterlyFiguresRating(0);
        }

        return fd;
    }

    private FundamentalData rateAnalystEstimation(FundamentalData fd) {
        double analystEstimation = fd.getAnalystEstimation();

        if (analystEstimation >= 2.5) {
            fd.setAnalystEstimationRating(1);
        } else if (analystEstimation <= 1.5) {
            fd.setAnalystEstimationRating(-1);
        } else {
            fd.setAnalystEstimationRating(0);
        }

        return fd;
    }

    private FundamentalData rateOverall(FundamentalData fd) {
        int overallRating = fd.getRoeRating()
                + fd.getEbitRating()
                + fd.getEquityRatioRating()
                + fd.getPer5yearsRating()
                + fd.getPerCurrentRating()
                + fd.getAnalystEstimationRating()
                + fd.getLastQuarterlyFiguresRating();

        fd.setOverallRating(overallRating);
        return fd;
    }

    private FundamentalData ratePerCurrent(FundamentalData fd) {
        double perCurrent = fd.getPerCurrent();

        if (perCurrent < 12) {
            fd.setPerCurrentRating(1);
        } else if (perCurrent > 16) {
            fd.setPerCurrentRating(-1);
        } else {
            fd.setPerCurrentRating(0);
        }

        return fd;
    }

    private FundamentalData ratePer5years(FundamentalData fd) {
        double per5years = fd.getPer5years();

        if (per5years < 12) {
            fd.setPer5yearsRating(1);
        } else if (per5years > 16) {
            fd.setPer5yearsRating(-1);
        } else {
            fd.setPer5yearsRating(0);
        }

        return fd;
    }

    private FundamentalData rateEquityRatio(FundamentalData fd) {
        double equityRatio = fd.getEquityRatio();

        if (equityRatio > 25) {
            fd.setEquityRatioRating(1);
        } else if (equityRatio < 15) {
            fd.setEquityRatioRating(-1);
        } else {
            fd.setEquityRatioRating(0);
        }

        return fd;
    }

    private FundamentalData rateEbit(FundamentalData fd) {
        double ebit = fd.getEbit();

        if (ebit > 12) {
            fd.setEbitRating(1);
        } else if (ebit < 6) {
            fd.setEbitRating(-1);
        } else {
            fd.setEbitRating(0);
        }

        return fd;
    }

    private FundamentalData rateRoe(FundamentalData fd) {
        double roe = fd.getRoe();

        if (roe > 20) {
            fd.setRoeRating(1);
        } else if (roe < 10) {
            fd.setRoeRating(-1);
        } else {
            fd.setRoeRating(0);
        }

        return fd;
    }
}
