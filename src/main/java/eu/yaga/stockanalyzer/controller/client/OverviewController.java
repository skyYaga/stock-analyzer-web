package eu.yaga.stockanalyzer.controller.client;

import eu.yaga.stockanalyzer.model.FundamentalData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashSet;

/**
 * Overview Page listing the stocks
 */
@Controller
public class OverviewController {

    @RequestMapping({"/", "/overview"})
    public String overview(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        LinkedHashSet<FundamentalData> fundamentalData = restTemplate.getForObject("http://localhost:8080/api/fundamental-data", LinkedHashSet.class);

        model.addAttribute("fundamentalData", fundamentalData);

        return "overview";
    }
}
