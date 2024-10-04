package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PositionServiceUnitTest {

    @Mock
    PositionDao positionDao;

    @Mock
    QuoteService quoteService;

    @Mock
    Quote quote;

    @Mock
    Position position;

    private PositionService positionService;

    @BeforeEach
    void init() {
        positionService = new PositionService(positionDao, quoteService);
    }

    @Test
    void positionServiceBuy_ValidNumberOfSharesTest() {
        String ticker = "MSFT";
        int numberOfShares = 44;
        double price = 15.0;

        when(position.getNumOfShares()).thenReturn(10);
        when(position.getValuePaid()).thenReturn(130.0);

        when(quote.getVolume()).thenReturn(45);

        when(positionDao.findById(ticker)).thenReturn(Optional.ofNullable(position));
        when(quoteService.fetchQuoteDataFromAPI(ticker)).thenReturn(Optional.ofNullable(quote));

        Position newPosition = positionService.buy(ticker, numberOfShares, price);

        assertNotNull(newPosition);
        assertEquals(ticker, newPosition.getTicker());
        assertEquals(54, newPosition.getNumOfShares());
        assertEquals(790, newPosition.getValuePaid());
    }

    @Test
    void positionServiceBuy_InvalidNumberOfSharesTest() {
        String ticker = "MSFT";
        int numberOfShares = 46;
        double price = 15.0;

        when(quote.getVolume()).thenReturn(45);

        when(positionDao.findById(ticker)).thenReturn(Optional.ofNullable(position));
        when(quoteService.fetchQuoteDataFromAPI(ticker)).thenReturn(Optional.ofNullable(quote));

        Position newPosition = positionService.buy(ticker, numberOfShares, price);

        assertNull(newPosition);
    }


}