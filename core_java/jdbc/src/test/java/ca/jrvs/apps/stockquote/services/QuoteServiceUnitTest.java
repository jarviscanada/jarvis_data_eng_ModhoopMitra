package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class QuoteServiceUnitTest {

    @Mock
    QuoteDao quoteDao;

    @Mock
    QuoteHttpHelper httpHelper;

    @Mock
    Quote quote;

    private QuoteService quoteService;

    @BeforeEach
    void init() {
        quoteService = new QuoteService(quoteDao, httpHelper);
    }

    @Test
    void quoteServiceFetchQuoteDataFromAPI_ValidTickerTest() {
        String ticker = "MSFT";
        when(httpHelper.fetchQuoteInfo(ticker)).thenReturn(quote);
        when(quoteDao.save(quote)).thenReturn(quote);

        Optional<Quote> result = quoteService.fetchQuoteDataFromAPI(ticker);

        assertTrue(result.isPresent());
    }

    @Test
    void quoteServiceFetchQuoteDataFromAPI_InvalidTickerTest() {
    String ticker = "bad symbol";
    when(httpHelper.fetchQuoteInfo(ticker)).thenThrow(new IllegalArgumentException());

    Optional<Quote> result = quoteService.fetchQuoteDataFromAPI(ticker);

    assertFalse(result.isPresent());

    }

}
