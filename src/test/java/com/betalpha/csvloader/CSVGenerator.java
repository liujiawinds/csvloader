package com.betalpha.csvloader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class CSVGenerator {

    private static ExecutorService executor = Executors.newFixedThreadPool(10, r -> {
        Thread t = Executors.defaultThreadFactory().newThread(r);
        t.setDaemon(true);
        return t;
    });

    private void generate(String fileName, final int lineCount) throws Exception {
        FileWriter fw = new FileWriter(fileName, true);
        final BufferedWriter bw = new BufferedWriter(fw);
        final AtomicLong count = new AtomicLong(0);
        for (int i = 0; i < 10; i++) {
            executor.execute(
                    () -> {
                        while (count.incrementAndGet() <= lineCount) {
                            try {
                                bw.write("000002,0.03,1.02,-1.01\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + "exit.");
                    }
            );
        }
        executor.awaitTermination(5, TimeUnit.SECONDS);
        bw.close();
        fw.close();
    }

    public static void main(String[] args) {
        try {
            new CSVGenerator().generate("data/2017-10-26.csv", 1000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
