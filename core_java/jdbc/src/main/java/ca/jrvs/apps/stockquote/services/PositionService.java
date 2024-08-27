package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.Quote;



import java.util.Optional;

public class PositionService {

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
                } else {
                    newPos.setNumOfShares(numberOfShares);
                    newPos.setValuePaid(numberOfShares * price);
                }
                dao.save(newPos);
                return newPos;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Sells all shares of the given ticker symbol
     * @param ticker symbol
     */
    public void sell(String ticker) {
        try {
            dao.deleteById(ticker);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    /**
     * Display all current positions
     */
    public Iterable<Position> showAll() {
        return dao.findAll();
    }

}
