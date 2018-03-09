package com.betalpha.csvloader.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {

    public static Properties load(String configFileName) throws Exception {
        InputStream in = PropertiesHelper.class.getClassLoader().getResourceAsStream(configFileName);
        Properties props = new Properties();
        try {
            props.load(in);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return props;
    }
}
