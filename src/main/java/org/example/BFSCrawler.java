package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class BFSCrawler {
    public static void main(String[] args) {
        String url = "https://www.youtube.com/";
        bfsCrawl(url);
    }

    private static void bfsCrawl(String startUrl) {
        // HashSet to keep track of visited URLs (O(1) lookups)
        HashSet<String> visitedUrls = new HashSet<>();
        // Queue to implement BFS (FIFO)
        Queue<String> queue = new ArrayDeque<>();

        // Add the start URL to the queue
        queue.add(startUrl);
        visitedUrls.add(startUrl);

        int depth = 0;
        int maxDepth = 5;

        while (!queue.isEmpty() && depth < maxDepth) {
            // Increment depth for each "level" of URLs processed
            int levelSize = queue.size();  // Number of URLs at the current depth

            System.out.println("Processing depth: " + depth);

            for (int i = 0; i < levelSize; i++) {
                String url = queue.poll();
                Document doc = request(url);

                if (doc != null) {
                    for (Element link : doc.select("a[href]")) {
                        String absUrl = link.absUrl("href");

                        // If the URL hasn't been visited, add it to the queue
                        if (!visitedUrls.contains(absUrl) && absUrl.startsWith("http")) {
                            queue.add(absUrl);
                            visitedUrls.add(absUrl);
                        }
                    }
                }
            }

            // Move to the next depth level
            depth++;
        }
    }

    private static Document request(String url) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if (con.response().statusCode() == 200) {
                System.out.println("Link: " + url);
                System.out.println("Title: " + doc.title());

                return doc;
            }
        } catch (IOException e) {
            System.err.println("Error accessing: " + url);
        }

        return null;
    }
}
