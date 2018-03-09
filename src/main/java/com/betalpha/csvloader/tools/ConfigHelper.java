package com.betalpha.csvloader.tools;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.List;

/**
 * Created by liujia on 2017/5/8.
 * Unify configuration interface.
 */
public class ConfigHelper {

    private static final Config instance;

    static {
        instance = ConfigFactory.load();
    }
    public static Config getInstance(){
        return instance;
    }
    private ConfigHelper(){
    }

    public static int getInt(String path) {
        return instance.getInt(path);
    }

    public static long getLong(String path) {
        return instance.getLong(path);
    }

    public static String getString(String path) {
        return instance.getString(path);
    }

    public static Config getConfig(String path) {
        return instance.getConfig(path);
    }

    public static List<String> getStringList(String path) {
        return instance.getStringList(path);
    }

    public static boolean getBoolean(String path) {
        return instance.getBoolean(path);
    }
}
