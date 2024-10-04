package ca.jrvs.apps.stockquote.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionDaoTest {

    private PositionDao positionDao;
    private Position position;

    @BeforeEach
    void init() throws SQLException {
        positionDao = new PositionDao(DriverManager.getConnection("jdbc:postgresql://localhost:5432/stock_quote", "postgres", "password"));
        position = new Position();
        position.setTicker("MSFT");
        position.setNumOfShares(10);
        position.setValuePaid(100.0);
    }

    @Test
    void quoteDaoSaveTest() {
        Position actual = positionDao.save(position);
        Position expected = position;
        assertEquals(expected, actual);
    }

}
