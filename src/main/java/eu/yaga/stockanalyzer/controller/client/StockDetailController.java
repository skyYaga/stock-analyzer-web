package eu.yaga.stockanalyzer.controller.client;

import eu.yaga.stockanalyzer.model.FundamentalData;
import eu.yaga.stockanalyzer.repository.FundamentalDataRepository;
import eu.yaga.stockanalyzer.service.StockRatingBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Page showing the detailed rating of a stock
 */
@Controller
class StockDetailController {

    private static final Logger log = LoggerFactory.getLogger(StockDetailController.class);

    @Autowired
    private FundamentalDataRepository fundamentalDataRepository;

    @Autowired
    private StockRatingBusinessService stockRatingBusinessService;

    @RequestMapping(value = "/stockdetail", method = RequestMethod.GET)
    public String stock(@RequestParam(value = "symbol") String symbol, Model model) {
        model.addAttribute("symbol", symbol);

        RestTemplate restTemplate = new RestTemplate();
        FundamentalData fundamentalData = restTemplate.getForObject("http://localhost:8080/api/fundamental-data/" + symbol, FundamentalData.class);

        model.addAttribute("fundamentalData", fundamentalData);

        return "stockdetail";
    }

    @RequestMapping(value = "/stockdetail/edit", method = RequestMethod.GET)
    public String editStock(@RequestParam(value = "symbol") String symbol, Model model) {
        model.addAttribute("symbol", symbol);

        RestTemplate restTemplate = new RestTemplate();
        FundamentalData fundamentalData = restTemplate.getForObject("http://localhost:8080/api/fundamental-data/" + symbol, FundamentalData.class);

        model.addAttribute("fundamentalData", fundamentalData);

        return "edit-stockdetail";
    }

    @RequestMapping(value = "/stockdetail/edit", params={"stockSubmit"})
    public String stockSubmit(@ModelAttribute FundamentalData fundamentalData, Model model) {
        log.info("in stockSubmit");
        FundamentalData workingFundamentalData = fundamentalDataRepository.findBySymbolOrderByDateDesc(fundamentalData.getSymbol());
        workingFundamentalData.setAnalystEstimation(fundamentalData.getAnalystEstimation());
        workingFundamentalData.setStockIndex(fundamentalData.getStockIndex());
        workingFundamentalData.setLastQuarterlyFigures(fundamentalData.getLastQuarterlyFigures());
        workingFundamentalData.setNextQuarterlyFigures(fundamentalData.getNextQuarterlyFigures());
        workingFundamentalData.setEarningsRevision(fundamentalData.getEarningsRevision());
        workingFundamentalData.setName(fundamentalData.getName());
        workingFundamentalData.setUrls(fundamentalData.getUrls());

        fundamentalDataRepository.save(workingFundamentalData);

        model.addAttribute("fundamentalData", workingFundamentalData);

        return "stockdetail";
    }

    @RequestMapping(value="/stockdetail/edit", params={"addURL"})
    public String addURL(final FundamentalData fundamentalData, final BindingResult bindingResult) {
        fundamentalData.getUrls().add("");
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

    @RequestMapping(value = "/stockdetail/rate", method = RequestMethod.GET)
    public String rateStock(@RequestParam(value = "symbol") String symbol, Model model) {
        model.addAttribute("symbol", symbol);

        RestTemplate restTemplate = new RestTemplate();
        FundamentalData fundamentalData = restTemplate.getForObject("http://localhost:8080/api/fundamental-data/" + symbol + "/refresh", FundamentalData.class);

        model.addAttribute("fundamentalData", fundamentalData);

        return "stockdetail";
    }

    @RequestMapping(value = "/stockdetail/delete")
    public String deleteStock(@RequestParam(value = "symbol") String symbol, Model model) {
        model.addAttribute("symbol", symbol);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8080/api/fundamental-data/" + symbol + "/delete");

        return "redirect:/overview";
    }
}
