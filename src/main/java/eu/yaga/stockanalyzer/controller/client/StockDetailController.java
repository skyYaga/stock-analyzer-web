package eu.yaga.stockanalyzer.controller.client;

import eu.yaga.stockanalyzer.model.FundamentalData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * Page showing the detailed rating of a stock
 */
@Controller
public class StockDetailController {

    @RequestMapping("/stockdetail")
    public String stock(@RequestParam(value = "symbol") String symbol, Model model) {
        model.addAttribute("symbol", symbol);

        RestTemplate restTemplate = new RestTemplate();
        FundamentalData fundamentalData = restTemplate.getForObject("http://localhost:8080/api/fundamental-data/" + symbol, FundamentalData.class);

        model.addAttribute("ask", fundamentalData.getAsk());
        model.addAttribute("businessYears", fundamentalData.getBusinessYears());
        model.addAttribute("date", fundamentalData.getDate());
        model.addAttribute("ebit", fundamentalData.getEbit());
        model.addAttribute("equityRatio", fundamentalData.getEquityRatio());
        model.addAttribute("per5years", fundamentalData.getPer5years());
        model.addAttribute("perCurrent", fundamentalData.getPerCurrent());
        model.addAttribute("roe", fundamentalData.getRoe());

        return "stockdetail";
    }
}
