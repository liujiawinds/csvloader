package com.betalpha.csvloader.services;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;

public class LoaderTest extends TestCase {


    @Test
    public void testFileName() {
        File[] files = new File("data").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".csv");
            }
        });
        for (File file : files) {
            System.out.println(file.getName().replace(".csv", ""));
        }
    }
}
