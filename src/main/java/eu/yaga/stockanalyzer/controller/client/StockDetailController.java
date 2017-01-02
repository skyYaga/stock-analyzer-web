package eu.yaga.stockanalyzer.controller.client;

import eu.yaga.stockanalyzer.model.FundamentalData;
import eu.yaga.stockanalyzer.model.FundamentalDataUrl;
import eu.yaga.stockanalyzer.model.StockType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Page showing the detailed rating of a stock
 */
@Controller
class StockDetailController {

    private static final Logger log = LoggerFactory.getLogger(StockDetailController.class);
    private static final String API_URL = "http://localhost:8081/api/";

    @RequestMapping(value = "/stockdetail/{symbol:.+}", method = RequestMethod.GET)
    public String stock(@PathVariable String symbol, Model model) {
        model.addAttribute("symbol", symbol);

        RestTemplate restTemplate = new RestTemplate();
        FundamentalData fundamentalData = restTemplate.getForObject(API_URL + "fundamental-data/" + symbol, FundamentalData.class);

        model.addAttribute("fundamentalData", fundamentalData);

        return "stockdetail";
    }

    @RequestMapping(value = "/stockdetail/edit/new", method = RequestMethod.GET)
    public String newStock(Model model) {
        FundamentalData fundamentalData = new FundamentalData();
        fundamentalData.setAnalystEstimation(2);
        fundamentalData.setStockType(StockType.LARGE_CAP);
        model.addAttribute("fundamentalData", fundamentalData);

        return "edit-stockdetail";
    }

    @RequestMapping(value = "/stockdetail/edit/{symbol:.+}", method = RequestMethod.GET)
    public String editStock(@PathVariable String symbol, Model model) {
        model.addAttribute("symbol", symbol);

        RestTemplate restTemplate = new RestTemplate();
        FundamentalData fundamentalData = restTemplate.getForObject(API_URL + "fundamental-data/" + symbol, FundamentalData.class);

        model.addAttribute("fundamentalData", fundamentalData);

        return "edit-stockdetail";
    }

    @RequestMapping(value = "/stockdetail/edit", params={"stockSubmit"})
    public String stockSubmit(@ModelAttribute FundamentalData fundamentalData, Model model) {
        log.info("in stockSubmit");
        RestTemplate restTemplate = new RestTemplate();
        FundamentalData workingFundamentalData = restTemplate.getForObject(API_URL + "fundamental-data/" + fundamentalData.getSymbol(), FundamentalData.class);
        if (workingFundamentalData == null) {
            workingFundamentalData = new FundamentalData();
            workingFundamentalData.setSymbol(fundamentalData.getSymbol());
        }
        workingFundamentalData.setAnalystEstimation(fundamentalData.getAnalystEstimation());
        workingFundamentalData.setAnalystEstimationCount(fundamentalData.getAnalystEstimationCount());
        workingFundamentalData.setStockIndex(fundamentalData.getStockIndex());
        workingFundamentalData.setLastQuarterlyFigures(fundamentalData.getLastQuarterlyFigures());
        workingFundamentalData.setNextQuarterlyFigures(fundamentalData.getNextQuarterlyFigures());
        workingFundamentalData.setEarningsRevision(fundamentalData.getEarningsRevision());
        workingFundamentalData.setName(fundamentalData.getName());
        workingFundamentalData.setUrls(fundamentalData.getUrls());
        workingFundamentalData.setStockType(fundamentalData.getStockType());
        workingFundamentalData.setAutomaticRating(true);

        restTemplate.postForObject(API_URL + "fundamental-data/", workingFundamentalData, FundamentalData.class);

        model.addAttribute("fundamentalData", workingFundamentalData);

        return "redirect:/stockdetail/" + workingFundamentalData.getSymbol();
    }

    @RequestMapping(value="/stockdetail/edit", params={"addURL"})
    public String addURL(final FundamentalData fundamentalData, final BindingResult bindingResult) {
        fundamentalData.getUrls().add(new FundamentalDataUrl());
        return "edit-stockdetail";
    }

    @RequestMapping(value="/stockdetail/edit", params={"removeURL"})
    public String removeURL(
            final FundamentalData fundamentalData, final BindingResult bindingResult,
            final HttpServletRequest req) {
        final Integer urlId = Integer.valueOf(req.getParameter("removeURL"));
        fundamentalData.getUrls().remove(urlId.intValue());
        return "edit-stockdetail";
    }

    @RequestMapping(value = "/stockdetail/rate/{symbol:.+}", method = RequestMethod.GET)
    public String rateStock(@PathVariable String symbol, Model model) {
        model.addAttribute("symbol", symbol);

        RestTemplate restTemplate = new RestTemplate();
        FundamentalData fundamentalData = restTemplate.getForObject(API_URL + "fundamental-data/" + symbol + "/refresh", FundamentalData.class);

        model.addAttribute("fundamentalData", fundamentalData);

        return "redirect:/stockdetail/" + symbol;
    }

    @RequestMapping(value = "/stockdetail/delete/{symbol:.+}")
    public String deleteStock(@PathVariable String symbol, Model model) {
        model.addAttribute("symbol", symbol);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(API_URL + "fundamental-data/" + symbol + "/delete");

        return "redirect:/overview";
    }

    @RequestMapping(value = "/stockdetail/enableratings/{symbol:.+}")
    public String enableRatings(@PathVariable String symbol, Model model) {
        model.addAttribute("symbol", symbol);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(API_URL + "fundamental-data/" + symbol + "/enableratings", FundamentalData.class);

        return "redirect:/overview";
    }

    // Total control - setup a model and return the view name yourself. Or
    // consider subclassing ExceptionHandlerExceptionResolver (see below).
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
}
