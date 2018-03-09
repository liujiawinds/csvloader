package com.betalpha.csvloader.services;

import com.betalpha.csvloader.bean.TimeSeries;
import com.betalpha.csvloader.dao.impl.TimeSeriesDaoImpl;
import com.betalpha.csvloader.tools.DataSourceManager;
import com.betalpha.csvloader.tools.JmxMetric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Sender implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(Sender.class);
    private TimeSeriesDaoImpl timeSeriesDao;
    private BlockingQueue<TimeSeries> inputQueue;

    public Sender(BlockingQueue<TimeSeries> inputQueue) {
        this.inputQueue = inputQueue;
        this.timeSeriesDao = new TimeSeriesDaoImpl(DataSourceManager.getDataSource());
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                List<TimeSeries> timeSeriesList = new ArrayList<>();
                int drainCount = inputQueue.drainTo(timeSeriesList, 100);
                if (drainCount > 0) {
                    long start = System.currentTimeMillis();
                    timeSeriesDao.bulkInsert(timeSeriesList);

                    logger.info("Sender insert {} records into db, cost {}ms ", drainCount, (System.currentTimeMillis() - start));
                    JmxMetric.getInstance().histogram("bulk-insert-size", drainCount);
                    JmxMetric.getInstance().histogram("bulk-insert-latency", System.currentTimeMillis() - start);
                    timeSeriesList.clear();
                }
            } catch (Exception e) {
                logger.error("Data persistent to database occurred error.", e);
            }
        }
    }
}
