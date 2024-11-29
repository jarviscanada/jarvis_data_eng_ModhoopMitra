package ca.jrvs.apps.stockquote.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionDao implements CrudDao<Position, String> {

    private Connection c;
    final Logger infoLogger = LoggerFactory.getLogger("infoLogger");
    final Logger errorLogger = LoggerFactory.getLogger("errorLogger");

    String INSERT =
            "INSERT INTO " +
                    "position (number_of_shares, value_paid, symbol) " +
                    "VALUES (?, ?, ?)";
    String UPDATE =
            "UPDATE position SET " +
                    "number_of_shares = ?, " +
                    "value_paid = ? " +
                    "WHERE symbol = ?";
    String SELECT_ONE = "SELECT * FROM position WHERE symbol = ?";
    String SELECT = "SELECT * FROM position";
    String DELETE_ONE = "DELETE FROM position WHERE symbol = ?";
    String DELETE = "DELETE FROM position";


    public PositionDao(Connection c) {
        this.c = c;
    }

    @Override
    public Position save(Position entity) throws IllegalArgumentException {
        try (PreparedStatement ps = this.c.prepareStatement(findById(entity.getTicker()).isPresent() ? UPDATE : INSERT)) {
            ps.setInt(1, entity.getNumOfShares());
            ps.setDouble(2, entity.getValuePaid());
            ps.setString(3, entity.getTicker());
            ps.executeUpdate();
            infoLogger.info("Saved Position: {}", entity.getTicker());
            return entity;
        } catch (SQLException e) {
            errorLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Position> findById(String s) throws IllegalArgumentException {
        try (PreparedStatement ps = this.c.prepareStatement(SELECT_ONE)) {
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Position position = positionBuilder(rs);
                infoLogger.info("Position found: {}", position.getTicker());
                return Optional.of(position);
            }
        } catch (SQLException e) {
            errorLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        infoLogger.info("Position {} not found.", s);
        return Optional.empty();
    }

    @Override
    public Iterable<Position> findAll() {
        List<Position> positionList = new ArrayList<>();
        try (PreparedStatement ps = this.c.prepareStatement(SELECT)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                positionList.add(positionBuilder(rs));
            }
            infoLogger.info("Positions found: {}", positionList);
            return positionList;
        } catch (SQLException e) {
            errorLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(String s) throws IllegalArgumentException {
        Optional<Position> pos = findById(s);
        if (!pos.isPresent()) {
            errorLogger.error("No shares owned for {}.", s);
            throw new IllegalArgumentException();
        }
        try (PreparedStatement ps = this.c.prepareStatement(DELETE_ONE)) {
            ps.setString(1, s);
            ps.execute();
            infoLogger.info("Deleted Position: {}", s);
        } catch (SQLException e) {
            errorLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try (PreparedStatement ps = this.c.prepareStatement(DELETE)) {
            ps.execute();
            infoLogger.info("Deleted all Positions.");
        } catch (SQLException e) {
            errorLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Position positionBuilder(ResultSet rs) throws SQLException {
        Position position = new Position();
        position.setTicker(rs.getString("symbol"));
        position.setNumOfShares(rs.getInt("number_of_shares"));
        position.setValuePaid(rs.getDouble("value_paid"));
        return position;
    }
}
