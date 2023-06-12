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

}
