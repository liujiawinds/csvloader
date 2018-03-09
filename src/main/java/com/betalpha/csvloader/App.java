package com.betalpha.csvloader;

import com.betalpha.csvloader.bean.TimeSeries;
import com.betalpha.csvloader.services.Decoder;
import com.betalpha.csvloader.services.Loader;
import com.betalpha.csvloader.services.Sender;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.betalpha.csvloader.tools.ConfigHelper.getInt;
import static com.betalpha.csvloader.tools.ConfigHelper.getString;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    private ExecutorService loaderExecutor = Executors.newFixedThreadPool(getInt("loader.number"), new ThreadFactoryBuilder().setNameFormat("loader-%d").build());
    private ExecutorService decoderExecutor = Executors.newFixedThreadPool(getInt("decoder.number"), new ThreadFactoryBuilder().setNameFormat("decoder-%d").build());
    private ExecutorService senderExecutor = Executors.newFixedThreadPool(getInt("sender.number"), new ThreadFactoryBuilder().setNameFormat("sender-%d").build());

    private final BlockingQueue<String> decodingQueue = new ArrayBlockingQueue<>(getInt("decoder.decoding-queue-size"));
    private final BlockingQueue<TimeSeries> sendingQueue = new ArrayBlockingQueue<>(getInt("decoder.sending-queue-size"));

    private void start () {

        File[] files = new File(getString("data-dir")).listFiles((dir, name) -> name.endsWith(".csv"));
        if (files == null || files.length == 0){
            logger.error("Source directory is empty!");
            return;
        }
        for (File file : files) {
            this.loaderExecutor.execute(new Loader(decodingQueue, file));
        }
        int decoderNumber = getInt("decoder.number");
        for (int i = 0; i < decoderNumber; i++) {
            this.decoderExecutor.execute(new Decoder(decodingQueue, sendingQueue));
        }
        int senderNumber = getInt("sender.number");
        for (int i = 0; i < senderNumber; i++) {
            this.senderExecutor.execute(new Sender(sendingQueue));
        }

    }

    /**
     * shutdown graceful, for jsvc
     */
    private void stop () {

    }


    public static void main(String[] args) {
        logger.info("start csv loader...");
        App server = new App();
        try {
            server.start();
            while (true) Thread.sleep(1000);
        } catch (Exception e) {
            logger.error("csv loader stopped.", e);
        }
        logger.info("csv loader stopped.");
    }
}
