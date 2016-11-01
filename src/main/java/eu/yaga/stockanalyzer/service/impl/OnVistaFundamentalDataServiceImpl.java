package eu.yaga.stockanalyzer.service.impl;

import eu.yaga.stockanalyzer.model.FundamentalData;
import eu.yaga.stockanalyzer.parser.OnVistaParser;
import eu.yaga.stockanalyzer.service.FundamentalDataService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Implementation of the {@link FundamentalDataService} getting data from onvista
 */
public class OnVistaFundamentalDataServiceImpl implements FundamentalDataService {

    @Autowired
    private
    OnVistaParser onVistaParser;

    private static final Logger log = LoggerFactory.getLogger(OnVistaFundamentalDataServiceImpl.class);


    private BufferedReader br = null;
    private InputStream inputStream = null;

    /**
     * This method returns fundamental data of the given stock
     *
     * @param symbol Symbol of the stock
     * @return fundamental data
     */
    @Override
    public FundamentalData getFundamentalData(String symbol) {
        URL url = getUrlForSymbol(symbol);
        String html = queryHTML(url);
        return onVistaParser.getFundamentalData(html, symbol);
    }

    private URL getUrlForSymbol(String symbol) {
        URL resultUrl = null;

        try {
            URL searchUrl =
                    new URL("http://www.onvista.de/onvista/boxes/assetSearch.json?doSubmit=Suchen&portfolioName=&searchValue=" + symbol);

            String html = queryHTML(searchUrl);
            JSONObject jsonObject = new JSONObject(html);
            JSONArray jsonArray = jsonObject.getJSONObject("onvista").getJSONObject("results").getJSONArray("asset");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                if (obj.getString("type").equals("Aktie")) {
                    String urlString = obj.getString("snapshotlink");
                    resultUrl = new URL(urlString.replace("/aktien/", "/aktien/fundamental/"));
                    break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return resultUrl;
    }

    private String queryHTML(URL url) {
        StringBuilder sb = new StringBuilder();

        try {
            URLConnection uc = url.openConnection();
            uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36");
            inputStream = uc.getInputStream();

            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
