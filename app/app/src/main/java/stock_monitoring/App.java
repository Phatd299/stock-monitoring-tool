import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {
    private static final String STOCK_SYMBOL = "^DJI";  // Dow Jones Industrial Average symbol
    private static final Deque<String> stockDataQueue = new ArrayDeque<>();

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        // Schedule a task to run every 5 seconds
        executorService.scheduleAtFixedRate(() -> {
            try {
                // Query Yahoo Finance for the stock data
                Stock stock = YahooFinance.get(STOCK_SYMBOL);

                // Extract relevant information
                String timestamp = String.valueOf(System.currentTimeMillis());
                String stockValue = "Timestamp: " + timestamp + ", Stock Price: " + stock.getQuote().getPrice();

                // Store stock value and timestamp in the queue
                stockDataQueue.offer(stockValue);

                // Print or log the retrieved data
                System.out.println(stockValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }
}
