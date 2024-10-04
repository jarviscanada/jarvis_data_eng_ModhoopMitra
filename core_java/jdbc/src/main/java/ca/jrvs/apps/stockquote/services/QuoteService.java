package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class QuoteService {

    final Logger infoLogger = LoggerFactory.getLogger("infoLogger");
    final Logger errorLogger = LoggerFactory.getLogger("errorLogger");

    private QuoteDao quoteDao;
    private QuoteHttpHelper httpHelper;

    public QuoteService(QuoteDao quoteDao, QuoteHttpHelper httpHelper) {
        this.quoteDao = quoteDao;
        this.httpHelper = httpHelper;
    }

    /**
     * Fetches latest quote data from endpoint.
     * @param ticker Ticker symbol
     * @return Latest quote information or empty optional if ticker symbol not found
     */
    public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {
        try {
            Quote quote = httpHelper.fetchQuoteInfo(ticker); // fetchQuoteInfo throws IllegalArgException if ticker does not exist
            return Optional.ofNullable(quoteDao.save(quote));
        } catch (IllegalArgumentException e) {
            errorLogger.error("Provided ticker is not valid.");
            throw new IllegalArgumentException();
        }
    }
}
