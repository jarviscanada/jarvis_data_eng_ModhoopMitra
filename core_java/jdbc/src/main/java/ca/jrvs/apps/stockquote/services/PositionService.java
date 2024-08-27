package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Optional;

public class PositionService {

    final Logger infoLogger = LoggerFactory.getLogger("infoLogger");
    final Logger errorLogger = LoggerFactory.getLogger("errorLogger");

    private PositionDao dao;
    private QuoteService quoteService; // need this to see available quote volume - also could be used to confirm price param

    public PositionService(PositionDao dao, QuoteService quoteService) {
        this.dao = dao;
        this.quoteService = quoteService;
    }

    /**
     * Processes a buy order and updates the database accordingly
     * @param ticker symbol
     * @param numberOfShares number of shares to buy
     * @param price price of share
     * @return The position in our database after processing the buy
     */
    public Position buy(String ticker, int numberOfShares, double price) {
        Position newPos = new Position();
        Optional<Position> pos = dao.findById(ticker);
        Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI(ticker);
        try {
            newPos.setTicker(ticker);
            if (quote.get().getVolume() >= numberOfShares) { // quote service throws illegalargexception, no need isPresent
                if (pos.isPresent()) {
                    newPos.setNumOfShares(pos.get().getNumOfShares() + numberOfShares);
                    newPos.setValuePaid(pos.get().getValuePaid() + (numberOfShares * price));
                    infoLogger.info("Bought shares. Existing shares updated.");
                } else {
                    newPos.setNumOfShares(numberOfShares);
                    newPos.setValuePaid(numberOfShares * price);
                    infoLogger.info("Bought shares.");
                }
                dao.save(newPos);
                return newPos;
            }
        } catch (Exception e) {
            errorLogger.error("Invalid ticker.");
            throw new RuntimeException(e);
        }
        errorLogger.error("Trying to buy more shares than available.");
        return null;
    }

    /**
     * Sells all shares of the given ticker symbol
     * @param ticker symbol
     */
    public void sell(String ticker) {
        try {
            dao.deleteById(ticker);
            infoLogger.info("Sold all {} shares.", ticker);
        } catch (Exception e) {
            errorLogger.error("No shares owned for {}", ticker);
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Display all current positions
     */
    public Iterable<Position> showAll() {
        infoLogger.info("Finding all current Positions.");
        return dao.findAll();
    }
}
