package ca.jrvs.apps.stockquote.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.Timestamp;

public class QuoteHttpHelper {

    final Logger infoLogger = LoggerFactory.getLogger("infoLogger");
    final Logger errorLogger = LoggerFactory.getLogger("errorLogger");

    private final String apiKey;
    private final OkHttpClient client;

    public QuoteHttpHelper(OkHttpClient client, String apiKey) {
        this.client = client;
        this.apiKey = apiKey;
    }

    /**
     * Fetch latest quote data from Alpha Vantage endpoint
     * @param symbol Ticker symbol
     * @return Quote with latest data
     * @throws IllegalArgumentException if no data was found for the given symbol
     */
    public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
        Request request = new Request.Builder()
                .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json")
                .header("X-RapidAPI-Key", this.apiKey)
                .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .build();

        Quote quote = new Quote();
        try (Response response = client.newCall(request).execute()) {
            infoLogger.info("Received response from Alpha Vantage API.");

            ObjectMapper om = new ObjectMapper();
            JsonNode resNode = om.readTree(response.body().string());
            JsonNode quoteNode = resNode.get("Global Quote");

            if (quoteNode == null || quoteNode.isEmpty()) {
                infoLogger.info("No data for this symbol: {}", symbol);
                throw new IllegalArgumentException();
            }

            quote = om.convertValue(quoteNode, Quote.class);
            quote.setTimestamp(new Timestamp(System.currentTimeMillis()));

        } catch (IOException e) {
            errorLogger.error(e.getMessage());
        }

        return quote;
    }
}
