### **README: Web Crawler Using DFS and BFS**

---

## **Project Overview**

This project demonstrates a basic web crawler implemented in Java using both **Depth-First Search (DFS)** and **Breadth-First Search (BFS)** algorithms. The web crawler starts from a given URL and recursively visits other URLs up to a certain depth/level. 

The DFS-based crawler explores each link deeply before backtracking, while the BFS-based crawler explores all links level by level, making the two algorithms well-suited for different use cases.

### **DFS-Based Crawler**
- In this crawler, the depth-first search algorithm is used to explore each URL and its links to a given depth.
- It recursively visits URLs, going as deep as possible on each branch before moving to the next link.

### **BFS-Based Crawler**
- In this version, the crawler uses the breadth-first search algorithm to explore all URLs at the current level before moving on to the next set of links.
- It is implemented using a queue to ensure URLs are visited in the correct order.

---

## **Project Structure**

```bash
.
├── src
│   └── org
│       └── example
│           ├── DFSCrawler.java  # DFS-based web crawler
│           ├── BFSCrawler.java  # BFS-based web crawler
├── README.md                    # Project documentation
└── pom.xml                      # Maven configuration file (if applicable)
```

---

## **DFS Crawler**

### **How it Works**

- The **DFS-based web crawler** is implemented using recursion. Starting from a given URL, it explores the first link it finds, goes as deep as possible along that link, and backtracks once it reaches a dead-end or a maximum depth (set to 5 in this case).
- Each visited URL is stored in a list to prevent revisiting the same page.

### **Code Explanation**

1. **Main Class**: `DFSCrawler.java`
2. The crawler starts by calling the `crawl` method.
3. The `crawl` method:
   - If the maximum depth (level) has not been reached, it fetches the web page using Jsoup.
   - It recursively visits all the links found on the page.
   - Each page title and URL are printed to the console as the crawler progresses.

### **DFS Crawler Code Snippet**

```java
public class DFSCrawler {
    public static void main(String[] args) {
        String url = "https://www.youtube.com/";
        crawl(1, url, new ArrayList<>());
    }

    private static void crawl(int level, String url, ArrayList<String> visitedUrls) {
        if (level <= 5) {
            Document doc = request(url, visitedUrls);
            if (doc != null) {
                for (Element link : doc.select("a[href]")) {
                    String hrefVal = link.absUrl("href");
                    if (!visitedUrls.contains(hrefVal)) {
                        crawl(++level, hrefVal, visitedUrls);
                    }
                }
            }
        }
    }

    private static Document request(String url, ArrayList<String> visited) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            if (con.response().statusCode() == 200) {
                System.out.println("Link: " + url);
                System.out.println("Title: " + doc.title());
                visited.add(url);
                return doc;
            }
        } catch (IOException e) {
            Logger.getGlobal().log(Level.WARNING, "Something bad happened!");
        }
        return null;
    }
}
```

---

## **BFS Crawler**

### **How it Works**

- The **BFS-based web crawler** processes each level of links at the same depth before moving to the next.
- It uses a queue data structure to maintain URLs for each level, ensuring the crawler explores all links at a given depth before proceeding further.

### **Code Explanation**

1. **Main Class**: `BFSCrawler.java`
2. The `crawl` method:
   - Starts by visiting the initial URL and adding it to the queue.
   - For each URL, it processes the page and extracts the links, adding them to the queue if they haven't been visited yet.
   - This process continues for each level, ensuring all links at one level are visited before going deeper.

### **BFS Crawler Code Snippet**

```java
public class BFSCrawler {
    public static void main(String[] args) {
        String url = "https://www.youtube.com/";
        bfsCrawl(url, 5);
    }

    private static void bfsCrawl(String startUrl, int maxLevel) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, Integer> urlLevel = new HashMap<>();

        queue.add(startUrl);
        visited.add(startUrl);
        urlLevel.put(startUrl, 0);

        while (!queue.isEmpty()) {
            String url = queue.poll();
            int level = urlLevel.get(url);
            if (level >= maxLevel) continue;

            Document doc = request(url);
            if (doc != null) {
                for (Element link : doc.select("a[href]")) {
                    String hrefVal = link.absUrl("href");
                    if (!visited.contains(hrefVal)) {
                        visited.add(hrefVal);
                        queue.add(hrefVal);
                        urlLevel.put(hrefVal, level + 1);
                    }
                }
            }
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
            Logger.getGlobal().log(Level.WARNING, "Something bad happened!");
        }
        return null;
    }
}
```

---

## **Complexity Analysis**

### **Time Complexity**
- Both DFS and BFS have a time complexity of **O(V + E)**, where:
  - `V` is the number of nodes (web pages).
  - `E` is the number of edges (links).

### **Space Complexity**
- **DFS**: **O(V)**, where `V` is the number of pages due to recursion depth and storing visited URLs.
- **BFS**: **O(V)**, due to the queue storing all URLs at each level and the list of visited URLs.

---

## **Dependencies**

- **Jsoup**: A Java library for working with real-world HTML. It provides a very convenient API for extracting and manipulating data using the best of DOM, CSS, and jQuery-like methods.

To include Jsoup in your project, you can use the following Maven dependency:

```xml
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.14.3</version>
</dependency>
```

---

## **Usage**

### **DFS Crawler**:
1. Compile and run the `DFSCrawler.java` class.
2. The crawler will start from a specified URL and recursively visit links, up to a depth of 5 levels.
3. All visited links will be printed in the console.

### **BFS Crawler**:
1. Compile and run the `BFSCrawler.java` class.
2. The crawler will start from a specified URL and explore all links level-by-level, up to 5 levels deep.
3. Visited links and their titles will be printed.

---

## **How to Run**

1. Clone this repository to your local machine.
2. Navigate to the project directory.
3. Run the web crawler of your choice (DFS or BFS):
   - For DFS:
     ```bash
     javac DFSCrawler.java
     java DFSCrawler
     ```
   - For BFS:
     ```bash
     javac BFSCrawler.java
     java BFSCrawler
     ```

---

## **License**

This project is licensed under the MIT License.

---

Feel free to modify and extend the crawlers to suit your specific use cases!

