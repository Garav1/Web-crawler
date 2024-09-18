# Web Crawler

This is a simple web crawler written in Java, using the [Jsoup](https://jsoup.org/) library to crawl through websites starting from a given URL. The program fetches web pages, extracts links, and recursively visits those links up to a specified depth.

## Features
- Crawls web pages recursively starting from a user-defined URL.
- Visits pages up to 5 levels deep.
- Prints the title and URL of each page visited.
- Avoids revisiting the same URL.

## Requirements
- Java 8 or higher
- [Jsoup Library](https://jsoup.org/) (v1.14.3 or later)

## Installation

1. **Clone the repository**:
    ```bash
    git clone <repository-url>
    ```

2. **Download Jsoup**:
   You can download the Jsoup library [here](https://jsoup.org/download), or use Maven:
   
   **Maven Dependency**:
   ```xml
   <dependency>
     <groupId>org.jsoup</groupId>
     <artifactId>jsoup</artifactId>
     <version>1.14.3</version>
   </dependency>
   ```

3. **Add Jsoup to Classpath**:
   - If you're using an IDE like IntelliJ or Eclipse, import the Jsoup JAR into your project.

## How to Run

1. **Compile the code**:
    ```bash
    javac -cp .:jsoup-1.14.3.jar org/example/crawler.java
    ```

2. **Run the crawler**:
    ```bash
    java -cp .:jsoup-1.14.3.jar org.example.crawler
    ```

3. The crawler starts by visiting the homepage of YouTube (`https://www.youtube.com/`) and recursively follows links up to 5 levels deep.

## Code Overview

- **`crawl` function**:
    - This function handles the recursive process of fetching pages and visiting links.
    - It prevents revisiting URLs by checking the `visitedUrls` list.
  
- **`request` function**:
    - This function sends an HTTP request to the provided URL and fetches the document.
    - If the HTTP response code is 200, it prints the page title and URL, and adds the URL to the visited list.

## Example Output

```bash
Link: https://www.youtube.com/
Title: YouTube

Link: https://www.youtube.com/about/
Title: About YouTube
...
```

## Limitations
- The depth of the crawl is limited to 5 levels.
- The crawler does not handle robots.txt or other crawling restrictions.
- External URLs are followed without any domain restrictions.

## License
This project is open source 
