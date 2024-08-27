package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class QuoteServiceIntTest {

    private QuoteService quoteService;

    @BeforeEach
    void init() throws SQLException {
        QuoteHttpHelper httpHelper = new QuoteHttpHelper(new OkHttpClient(), "72b2ed9592mshc367cfa394828c0p1edd70jsnf043860ef6d3");
        QuoteDao quoteDao = new QuoteDao(DriverManager.getConnection("jdbc:postgresql://localhost:5432/stock_quote", "postgres", "password"));
        quoteService = new QuoteService(quoteDao, httpHelper);
    }

    @Test
    void quoteServiceFetchQuoteDataFromAPI_ValidTickerTest() {
        String ticker = "MSFT";
        Optional<Quote> actual = quoteService.fetchQuoteDataFromAPI(ticker);
        assertTrue(actual.isPresent());
        assertEquals(ticker, actual.get().getTicker());
    }

    @Test
    void quoteServiceFetchQuoteDataFromAPI_InvalidTickerTest() {
        String ticker = "bad symbol";
        assertThrows(IllegalArgumentException.class, () -> quoteService.fetchQuoteDataFromAPI(ticker));
    }
}
