import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Paste your url that you want to crawl");
        String url = scanner.next();
        System.out.println("Input phrases you want to look for. Please use pattern like this:\nTest,Love,Example");
        String[] phrases = scanner.next().split(",");
        System.out.println("Input max depth for crawler");
        int depth = scanner.nextInt();
        System.out.println("Input page limit for crawler");
        int limit = scanner.nextInt();
        Crawler crawler = new Crawler(url,depth,limit, phrases);

        CSVWorker worker = new CSVWorker("data.csv", crawler.getPhraseCount());
        CSVWorker worker1 = new CSVWorker("topData.csv", crawler.getSerializedPhraseCount());
    }
}
