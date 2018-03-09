package com.betalpha.csvloader.services;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Loader implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(Loader.class);
    private BlockingQueue<String> outputQueue;
    private File fileRef = null;

    public Loader(BlockingQueue<String> outputQueue, File fileRef) {
        this.fileRef = fileRef;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        try {
            LineIterator it = FileUtils.lineIterator(fileRef, "UTF-8");
            try {
                logger.info("Begin to read {}", fileRef.getAbsolutePath());
                while (it.hasNext()) {
                    String line = it.nextLine();

                    // append trading date to string
                    String tradingDate = fileRef.getName().replace(".csv", "");
                    outputQueue.put(String.format("%s,%s", line, tradingDate));
                }
                logger.info("Reading end. {}", fileRef.getAbsolutePath());
            } finally {
                LineIterator.closeQuietly(it);
            }
        } catch (Exception e) {
            logger.error("Load data from file occurred error.", e);
        }
    }
}
