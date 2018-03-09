package com.betalpha.csvloader.dao;

import com.betalpha.csvloader.bean.TimeSeries;

import java.sql.SQLException;
import java.util.List;

public interface TimeSeriesDao {
    public void bulkInsert(List<TimeSeries> timeSeriesList) throws SQLException;
}
