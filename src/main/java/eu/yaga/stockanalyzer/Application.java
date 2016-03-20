package eu.yaga.stockanalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application class
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... strings) throws Exception {



        /*String query = "select * from yahoo.finance.historicaldata where symbol = \"APC.DE\" and startDate = \"2009-09-11\" and endDate = \"2010-03-10\"";
        String fullUrlStr = baseUrl + URLEncoder.encode(query, "UTF-8") + "&format=json&env=store://datatables.org/alltableswithkeys";

        URL fullUrl = new URL(fullUrlStr);
        InputStream is = fullUrl.openStream();

        JSONTokener tok = new JSONTokener(is);
        JSONObject result = new JSONObject(tok);
        ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();
        JSONArray resultArray = (JSONArray) result.getJSONObject("query").getJSONObject("results").get("quote");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject o = (JSONObject) resultArray.get(i);
            ExchangeRate er = new ExchangeRate();

            er.setSymbol(o.getString("Symbol"));
            er.setDate(sdf.parse(o.getString("Date")));
            er.setClose(o.getDouble("Close"));
            exchangeRates.add(er);
        }
        is.close();
        log.info(exchangeRates.get(0).toString());*/
    }
}
