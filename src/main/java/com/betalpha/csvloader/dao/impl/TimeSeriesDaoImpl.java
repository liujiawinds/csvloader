package com.betalpha.csvloader.dao.impl;

import com.betalpha.csvloader.bean.TimeSeries;
import com.betalpha.csvloader.dao.TimeSeriesDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class TimeSeriesDaoImpl implements TimeSeriesDao {
    private static final String INSERT_SQL = "insert into time_series_data values(?, ?, ?, ?)";

    private final DataSource dataSource;

    public TimeSeriesDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void bulkInsert(List<TimeSeries> timeSeriesList) throws SQLException {
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(INSERT_SQL, Statement.NO_GENERATED_KEYS);
            for (TimeSeries timeSeries : timeSeriesList) {
                ps.setString(1, timeSeries.getUuid());
                ps.setDate(2, new Date(timeSeries.getTradingDate().getTime()));
                ps.setString(3, timeSeries.getStockCode());
                ps.setDouble(4, timeSeries.getItemValue());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
