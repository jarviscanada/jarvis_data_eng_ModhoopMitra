package ca.jrvs.apps.stockquote.dao;


import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Timestamp;


public class QuoteHttpHelperTest {

    private QuoteHttpHelper qhh;
    private static final String apiKey = "72b2ed9592mshc367cfa394828c0p1edd70jsnf043860ef6d3";

    @BeforeEach
    void init() {
        qhh = new QuoteHttpHelper(new OkHttpClient(), apiKey);
    }

    @Test
    void QuoteMsftTest() {
        Quote expected = new Quote();
        expected.setTicker("MSFT");
        expected.setOpen(418.96);
        expected.setHigh(421.75);
        expected.setLow(416.46);
        expected.setPrice(421.53);
        expected.setVolume(15233957);
        expected.setLatestTradingDay(Date.valueOf("2024-08-19"));
        expected.setPreviousClose(418.47);
        expected.setChange(3.06);
        expected.setChangePercent("0.7312%");
        expected.setTimestamp(new Timestamp(System.currentTimeMillis()));

        Quote actual = qhh.fetchQuoteInfo("MSFT");

        assertEquals(expected.getTicker(), actual.getTicker());
        assertEquals(expected.getOpen(), actual.getOpen());
        assertEquals(expected.getHigh(), actual.getHigh());
        assertEquals(expected.getLow(), actual.getLow());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getVolume(), actual.getVolume());
        assertEquals(expected.getLatestTradingDay(), actual.getLatestTradingDay());
        assertEquals(expected.getPreviousClose(), actual.getPreviousClose());
        assertEquals(expected.getChange(), actual.getChange());
        assertEquals(expected.getChangePercent(), actual.getChangePercent());
        assertNotNull(actual.getTimestamp());
    }
}
