package eu.yaga.stockanalyzer.service.impl;

import eu.yaga.stockanalyzer.model.FundamentalData;
import eu.yaga.stockanalyzer.parser.OnVistaParser;
import eu.yaga.stockanalyzer.service.FundamentalDataService;
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
    OnVistaParser onVistaParser;

    private static final Logger log = LoggerFactory.getLogger(OnVistaFundamentalDataServiceImpl.class);


    BufferedReader br = null;
    InputStream inputStream = null;

    /**
     * This method returns fundamental data of the given stock
     *
     * @param symbol Symbol of the stock
     * @return fundamental data
     */
    @Override
    public FundamentalData getFundamentalData(String symbol) {
        String html = getHTML(symbol);
        FundamentalData fundamentalData = onVistaParser.getFundamentalData(html, symbol);
        return fundamentalData;
    }

    private String getHTML(String symbol) {
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL("http://www.onvista.de/aktien/fundamental/Apple-Aktie-US0378331005");
            URLConnection uc = url.openConnection();
            uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36");
            inputStream = uc.getInputStream();

            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
