package com.betalpha.csvloader.services;

import com.betalpha.csvloader.bean.TimeSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class Decoder implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(Decoder.class);
    private BlockingQueue<String> inputQueue;
    private BlockingQueue<TimeSeries> outputQueue;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Decoder(BlockingQueue<String> inputQueue, BlockingQueue<TimeSeries> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String rawData = inputQueue.take();
                logger.debug("Begin to decode raw data: {}", rawData);

                String[] fields = rawData.split(",");
                Date tradingDate = transfer(fields[fields.length-1]);
                outputQueue.put(new TimeSeries(UUID.randomUUID().toString(), tradingDate, fields[0], Double.parseDouble(fields[1])));
            } catch (Exception e) {
                logger.error("Raw data decoder error.", e);
            }
        }
    }

    private Date transfer(String date) {
        LocalDate yyyyMMdd = LocalDate.parse(date, formatter);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = yyyyMMdd.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }
}
