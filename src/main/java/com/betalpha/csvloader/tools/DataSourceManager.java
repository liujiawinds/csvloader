package com.betalpha.csvloader.tools;

import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import javax.sql.DataSource;

/**
 * Created by liujia on 2018/3/9.
 */
public class DataSourceManager {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceManager.class);
    private static DataSource dataSource;

    static {
        if (dataSource == null) {
            try {
                Properties dataSourceProps = PropertiesHelper.load("db.properties");
                dataSource = new DataSourceFactory().createDataSource(dataSourceProps);
            } catch (Exception e) {
                logger.error("MySQL database connection pool initialize failed.", e);
            }
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
