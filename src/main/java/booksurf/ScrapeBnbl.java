package booksurf.booksurf;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ScrapeBnbl{
    public ArrayList<ArrayList<String>> scrapeBnbl(String siteSearch) throws IOException {
        // TODO Auto-generated constructor stub
        String storeUrl = "http://www.barnesandnoble.com/s/";
        ArrayList<String> titleList = new ArrayList<>();
        ArrayList<String> linksList = new ArrayList<>();
        ArrayList<String> priceList = new ArrayList<>();
        ArrayList<ArrayList<String>> bookElems = new ArrayList<>();
        String url = storeUrl + URLEncoder.encode(siteSearch, "UTF-8");
        Document document;
        document = org.jsoup.Jsoup.connect(url)
                .userAgent(DBInterface.userAgent)
                .get();

        Elements bookTitles = document.select("img[src]");
        for (Element bookTitle : bookTitles) {
            if (bookTitle.attr("alt").contains("Title: ")) {
                titleList.add(bookTitle.attr("alt").replaceAll("Title: ", ""));
            }
        }

        Elements bookPrices = document.select("a[aria-describedby]");
        for (Element bookPrice : bookPrices) {
            if (bookPrice.text().contains("$")) {
                    linksList.add("http://www.barnesandnoble.com" + bookPrice.attr("href"));
                    priceList.add(bookPrice.text());
            }
        }

        bookElems.add(titleList);
        bookElems.add(linksList);
        bookElems.add(priceList);		

        return bookElems;
    }
}