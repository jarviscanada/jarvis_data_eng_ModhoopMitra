package ca.jrvs.apps.stockquote.services;


import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PositionServiceIntTest {

    private PositionService positionService;

    @BeforeEach
    void init() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/stock_quote", "postgres", "password");
        QuoteHttpHelper httpHelper = new QuoteHttpHelper(new OkHttpClient(), "72b2ed9592mshc367cfa394828c0p1edd70jsnf043860ef6d3");

        QuoteDao quoteDao = new QuoteDao(connection);
        PositionDao positionDao = new PositionDao(connection);

        QuoteService quoteService = new QuoteService(quoteDao, httpHelper);

        positionService = new PositionService(positionDao, quoteService);
    }

    @Test
    void positionServiceBuy_ValidNumberOfSharesTest() {
        String ticker = "MSFT";
        Position actual = positionService.buy(ticker, 1, 5.0);
        assertNotNull(actual);
        assertEquals(ticker, actual.getTicker());
    }

    @Test
    void positionServiceBuy_InvalidNumberOfSharesTest() {
        String ticker = "MSFT";
        Position actual = positionService.buy(ticker, 999999999, 5.0);
        assertNull(actual);
    }
}
