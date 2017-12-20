package booksurf.booksurf;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

public class DBInterface {
    static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36";

    public static void main(String[] args)
            throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        String searchStr = args[0];
        ArrayList<ArrayList<ArrayList<String>>> consList = new ArrayList<>();
        consList.add(new ScrapeAmzn().scrapeAmzn(searchStr));
        consList.add(new ScrapeBnbl().scrapeBnbl(searchStr));
        consList.add(new ScrapeEbay().scrapeEbay(searchStr));
        consList.add(new ScrapeYtbe().scrapeYtbe(searchStr));
        System.out.println(generateCSV(consList)); //CSV Output
    }

    public static String generateCSV(ArrayList<ArrayList<ArrayList<String>>> consInput) {
        String outCSV = "";
        for (int i = 0; i < consInput.size(); i++) {
            for (int k = 0; k < consInput.get(i).size(); k++) {
                outCSV += "\"" + consInput.get(i).get(1).get(k) + "\", " +
				          "\"" + consInput.get(i).get(0).get(k) + "\", " +
                          "\"" + consInput.get(i).get(2).get(k).replace("$", "") + "\"\n";
            }
        }
        return outCSV;	
    }
}