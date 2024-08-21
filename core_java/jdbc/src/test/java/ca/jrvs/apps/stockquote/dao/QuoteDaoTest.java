package ca.jrvs.apps.stockquote.dao;


import okhttp3.OkHttpClient;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuoteDaoTest {

    private QuoteDao quoteDao;
    private Quote quote;

    @BeforeEach
    void init() throws SQLException {
        quoteDao = new QuoteDao(DriverManager.getConnection("jdbc:postgresql://localhost:5432/stock_quote", "postgres", "password"));
        quote = new QuoteHttpHelper(new OkHttpClient(), "72b2ed9592mshc367cfa394828c0p1edd70jsnf043860ef6d3").fetchQuoteInfo("MSFT");
    }

    @Test
    void QuoteDaoSaveTest() {
        Quote expected = quoteDao.save(quote);
        Quote actual = quote;
        assertEquals(expected, actual);
    }

    @Test void QuoteDaoFindByIdTest() {
        Optional<Quote> expected = quoteDao.findById("MSFT");
        assertNotNull(expected);
    }

    @Test
    void QuoteDaoFindAll() {
        Iterable<Quote> expected = quoteDao.findAll();
        assertNotNull(expected);
    }

}
