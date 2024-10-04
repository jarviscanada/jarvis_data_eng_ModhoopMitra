package ca.jrvs.apps.stockquote.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class QuoteDao implements CrudDao<Quote, String> {

    private Connection c;
    final Logger infoLogger = LoggerFactory.getLogger("infoLogger");
    final Logger errorLogger = LoggerFactory.getLogger("errorLogger");

    String INSERT =
            "INSERT INTO " +
                    "quote (open, high, low, price, volume, latest_trading_day, previous_close, change, change_percent, timestamp, symbol) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String UPDATE =
            "UPDATE quote SET " +
                    "open = ?, " +
                    "high = ?, " +
                    "low = ?, " +
                    "price = ?, " +
                    "volume = ?, " +
                    "latest_trading_day = ?, " +
                    "previous_close = ?, " +
                    "change = ?, " +
                    "change_percent = ?, " +
                    "timestamp = ? " +
                    "WHERE symbol = ?";
    String SELECT_ONE = "SELECT * FROM quote WHERE symbol = ?";
    String SELECT = "SELECT * FROM quote";
    String DELETE_ONE = "DELETE FROM quote WHERE symbol = ?";
    String DELETE = "DELETE FROM quote";


    public QuoteDao(Connection c) {
        this.c = c;
    }

    @Override
    public Quote save(Quote entity) throws IllegalArgumentException {

        try (PreparedStatement ps = this.c.prepareStatement(findById(entity.getTicker()).isPresent() ? UPDATE : INSERT)) {
            ps.setDouble(1, entity.getOpen());
            ps.setDouble(2, entity.getHigh());
            ps.setDouble(3, entity.getLow());
            ps.setDouble(4, entity.getPrice());
            ps.setInt(5, entity.getVolume());
            ps.setDate(6, entity.getLatestTradingDay());
            ps.setDouble(7, entity.getPreviousClose());
            ps.setDouble(8, entity.getChange());
            ps.setString(9, entity.getChangePercent());
            ps.setTimestamp(10, entity.getTimestamp());
            ps.setString(11, entity.getTicker());
            ps.executeUpdate();
            infoLogger.info("Saved Quote: {}", entity.getTicker());
            return entity;
        } catch (SQLException e) {
            errorLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Quote> findById(String s) throws IllegalArgumentException {

        try (PreparedStatement ps = this.c.prepareStatement(SELECT_ONE)) {
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Quote quote = quoteBuilder(rs);
                infoLogger.info("Quote found: {}", quote.getTicker());
                return Optional.of(quote);
            }

        } catch (SQLException e) {
            errorLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        infoLogger.info("Quote {} not found.", s);
        return Optional.empty();
    }

    @Override
    public Iterable<Quote> findAll() {
        List<Quote> quoteList = new ArrayList<>();
        try (PreparedStatement ps = this.c.prepareStatement(SELECT)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                quoteList.add(quoteBuilder(rs));
            }
            infoLogger.info("Quotes found: {}", quoteList);
            return quoteList;
        } catch (SQLException e) {
            errorLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(String s) throws IllegalArgumentException {
        try (PreparedStatement ps = this.c.prepareStatement(DELETE_ONE)) {
            ps.setString(1, s);
            ps.execute();
            infoLogger.info("Deleted Quote {}", s);
        } catch (SQLException e) {
            errorLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try (PreparedStatement ps = this.c.prepareStatement(DELETE)) {
            ps.execute();
            infoLogger.info("Deleted all Quotes.");
        } catch (SQLException e) {
            errorLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Quote quoteBuilder(ResultSet rs) throws SQLException {
        Quote quote = new Quote();
        quote.setTicker(rs.getString("symbol"));
        quote.setOpen(rs.getDouble("open"));
        quote.setHigh(rs.getDouble("high"));
        quote.setLow(rs.getDouble("low"));
        quote.setPrice(rs.getDouble("price"));
        quote.setVolume(rs.getInt("volume"));
        quote.setLatestTradingDay(rs.getDate("latest_trading_day"));
        quote.setPreviousClose(rs.getDouble("previous_close"));
        quote.setChange(rs.getDouble("change"));
        quote.setChangePercent(rs.getString("change_percent"));
        quote.setTimestamp(rs.getTimestamp("timestamp"));
        return quote;
    }
}
