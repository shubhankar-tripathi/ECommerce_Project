package booksurf.booksurf;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ScrapeEbay{
    public ArrayList<ArrayList<String>> scrapeEbay(String siteSearch) throws IOException {
        String storeUrl = "http://www.ebay.com/sch/i.html?_nkw=";
        ArrayList<String> titleList = new ArrayList<>();
        ArrayList<String> linksList = new ArrayList<>();
        ArrayList<String> priceList = new ArrayList<>();
        ArrayList<ArrayList<String>> bookElems = new ArrayList<>();
        String url = storeUrl + URLEncoder.encode(siteSearch, "UTF-8");
        Document document;
        document = org.jsoup.Jsoup.connect(url)
                .userAgent(DBInterface.userAgent)
                .get();

        Elements bookTitles = document.select("a[href]");
        for (Element bookTitle : bookTitles) {
            if (bookTitle.attr("class").contains("vip")) {
                titleList.add(bookTitle.text());
                linksList.add(bookTitle.attr("href"));
            }
        }

        Elements bookPrices = document.select("span[class]");
        for (Element bookPrice : bookPrices) {
            if (bookPrice.attr("class").contains("bold")) {
                priceList.add(bookPrice.text());
            }
        }

        bookElems.add(titleList);
        bookElems.add(linksList);
        bookElems.add(priceList);

        return bookElems;
    }
}