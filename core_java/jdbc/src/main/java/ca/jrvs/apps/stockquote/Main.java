package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.services.PositionService;
import ca.jrvs.apps.stockquote.services.QuoteService;
import okhttp3.OkHttpClient;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Map<String, String> properties = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("jdbc/src/main/resources/properties.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(":");
                properties.put(tokens[0], tokens[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Class.forName(properties.get("db-class"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        OkHttpClient client = new OkHttpClient();
        String url = "jdbc:postgresql://"+properties.get("server")+":"+properties.get("port")+"/"+properties.get("database");
        try (Connection c = DriverManager.getConnection(url, properties.get("username"), properties.get("password"))) {
            QuoteDao quoteRepo = new QuoteDao(c);
            PositionDao positionRepo = new PositionDao(c);
            QuoteHttpHelper rcon = new QuoteHttpHelper(client, properties.get("api-key"));
            QuoteService quoteService = new QuoteService(quoteRepo, rcon);
            PositionService positionService = new PositionService(positionRepo, quoteService);
            StockQuoteController con = new StockQuoteController(quoteService, positionService);
            con.initClient();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
