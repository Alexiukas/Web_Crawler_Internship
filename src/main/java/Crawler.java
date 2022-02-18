import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.util.*;

public class Crawler {
    private final int maxDepth;
    private final int pageLimit;
    private final String[] phrases;
    private final List<String> visitedURLs = new ArrayList<>();
    private final List<String[]> phraseCount = new ArrayList<>();
    private final String[] headers;

    public Crawler(String url, int maxDepth, int pageLimit, String[] phrases){
        this.maxDepth = maxDepth;
        this.pageLimit = pageLimit;
        this.phrases = phrases;

        headers = new String[phrases.length + 1];
        headers[0] = "URL";
        for(int i = 1;i < phrases.length + 1; i++){
            headers[i] = phrases[i-1];
        }
        phraseCount.add(headers);
        crawl(1, url);
    }


    private void crawl (int level, String url){
        if(level <= maxDepth){
            if(visitedURLs.size() >= pageLimit){
                System.out.println("limit of " + pageLimit +" has bean reached");
                return;
            }
            Document doc = request(url);
            if (doc != null){
                for (Element link : doc.select("a[href]")){
                    String nexLink = link.absUrl("href");
                    if(!visitedURLs.contains(nexLink)){
                        crawl(level++, nexLink);
                    }
                }
            }
        }
    }

    private Document request(String url){
        try{
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            if(con.response().statusCode() == 200){
                System.out.println("Link: " + url);
                visitedURLs.add(url);
                phraseChecker(doc.body().text(), url);
                return doc;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private void phraseChecker(String text, String url){
        String[] count = new String[phrases.length + 1];
        int index = 1;
        count[0] = url;
        for (String x : phrases){
            count[index++] = String.valueOf(StringUtils.countMatches(text.toLowerCase(), x.toLowerCase()));
        }
        phraseCount.add(count);
    }

    public List<String[]> getPhraseCount(){
        return phraseCount;
    }

    public List<String[]> getSerializedPhraseCount(){
        List<String[]> top = new ArrayList<>();
        for(int j = 1; j<phraseCount.size();j++){
            int count = 0;
            String[] hits = new String[phraseCount.get(0).length+1];
            hits[0] = phraseCount.get(j)[0];
            for(int i = 0; i < phraseCount.get(0).length-1; i++){
                count += Integer.parseInt(phraseCount.get(j)[i + 1]);
                hits[i + 1] = phraseCount.get(j)[i + 1];
            }
            hits[hits.length - 1] = String.valueOf(count);
            top.add(hits);
        }
        top.sort(Comparator.comparing(a -> Integer.valueOf(a[a.length - 1])));
        List<String[]> topTen = new ArrayList<>();
        topTen.add(headers);
        int size = top.size();
        for (int i = 1; i < 11; i++){
            topTen.add(top.remove(size-i));
        }
        return topTen;
    }
}
