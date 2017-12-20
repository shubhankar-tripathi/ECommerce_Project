package booksurf.booksurf;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ScrapeYtbe {
    public ArrayList<ArrayList<String>> scrapeYtbe(String siteSearch) throws IOException {
        String storeUrl = "https://www.youtube.com/results?search_query=audiobook+";
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
            if (bookTitle.attr("class").contains("yt-uix-tile-link yt-ui-ellipsis yt-ui-ellipsis-2 yt-uix-sessionlink      spf-link")) {
                    titleList.add(bookTitle.attr("title"));
                    linksList.add("https://www.youtube.com" + bookTitle.attr("href"));
                    priceList.add("Audiobook");
            }
        }

        bookElems.add(titleList);
        bookElems.add(linksList);
        bookElems.add(priceList);		

        return bookElems;
    }
}