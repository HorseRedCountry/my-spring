package com.majortom.myspring.chapter2.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>
 * 属性读取 工具类
 * </p>
 *
 * @author Major Tom
 * @date 2023/6/12 23:51
 **/
public final class PropUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropUtil.class);

    /**
     * 加载属性文件
     *
     * @param fileName 文件名
     * @return 属性信息
     */
    public static Properties loadProperties(String fileName) {
        Properties prop = null;
        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (null == in) {
                throw new FileNotFoundException(fileName + "file is not found");
            }
            prop = new Properties();
            prop.load(in);
        } catch (IOException e) {
            LOGGER.error("Load properties file failure", e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("Close input stream failure", e);
                }
            }
        }
        return prop;
    }

    /**
     * 获取字符型属性
     *
     * @param prop 配置信息
     * @param key  关键字
     * @return 关键字对应的属性值
     */
    public static String getString(Properties prop, String key) {
        return getString(prop, key, "");
    }

    /**
     * 获取字符型属性（可指定默认值）
     *
     * @param prop         配置信息
     * @param key          关键字
     * @param defaultValue 默认值
     * @return 属性值
     */
    public static String getString(Properties prop, String key, String defaultValue) {
        String value = defaultValue;
        if (prop.contains(key)) {
            value = prop.getProperty(key);
        }
        return value;
    }

    /**
     * 获取数值型属性(默认值为0)
     *
     * @param prop 配置信息
     * @param key  关键字
     * @return 属性值
     */
    public static int getInt(Properties prop, String key) {
        return getInt(prop, key, 0);
    }

    /***
     * 获取数值型属性
     * @param prop 配置新新
     * @param key 关键字
     * @param defaultValue 默认值
     * @return 属性值
     */
    public static int getInt(Properties prop, String key, int defaultValue) {
        int value = defaultValue;
        if (prop.contains(key)) {
            value = CastUtil.castInt(prop.getProperty(key));
        }
        return value;
    }

    /**
     * 获取布尔型属性(默认值为false)
     *
     * @param prop 配置信息
     * @param key  关键字
     * @return 属性值
     */
    public static boolean getBoolean(Properties prop, String key) {
        return getBoolean(prop, key, false);
    }

    /***
     * 获取布尔型属性
     * @param prop 配置新新
     * @param key 关键字
     * @param defaultValue 默认值
     * @return 属性值
     */
    public static boolean getBoolean(Properties prop, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (prop.contains(key)) {
            value = CastUtil.castBoolean(prop.getProperty(key));
        }
        return value;
    }

}
