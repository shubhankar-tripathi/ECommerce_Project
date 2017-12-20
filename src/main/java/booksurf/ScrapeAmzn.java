package booksurf.booksurf;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ScrapeAmzn{
    public ArrayList<ArrayList<String>> scrapeAmzn(String siteSearch) throws IOException {
        String storeUrl = "https://www.amazon.com/gp/aw/s/ref=is_box_?k=";
        ArrayList<String> titleList = new ArrayList<>();
        ArrayList<String> linksList = new ArrayList<>();
        ArrayList<String> priceList = new ArrayList<>();
        ArrayList<ArrayList<String>> bookElems = new ArrayList<>();
        String url =  storeUrl + URLEncoder.encode(siteSearch, "UTF-8");
        Document document = Jsoup.connect(url)
                .userAgent(DBInterface.userAgent)
                .get();

        Elements bookPage = document.select("form[method]");
        for (Element bookPost : bookPage) {
            if (bookPost.attr("method").contains("post")) {
                Document subDoc = Jsoup.parse(bookPost.outerHtml());
                Elements subTitle = subDoc.select("a[href]");
                for (Element subPost : subTitle) {
                    if (!subPost.text().contains("Used")){
                        titleList.add(subPost.text());
                        linksList.add("https://www.amazon.com" + subPost.attr("href"));
                    }
                }
                Elements subPrice = subDoc.select("b");
                for (Element subPost : subPrice) {
                    if (subPost.text().contains("$") &&
                        !subPost.text().contains("$0.00")){
                        priceList.add(subPost.text());
                    }
                }
            }
        }

        bookElems.add(titleList);
        bookElems.add(linksList);
        bookElems.add(priceList);		

        return bookElems;
    }
}