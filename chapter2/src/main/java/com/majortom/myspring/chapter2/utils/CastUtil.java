package com.majortom.myspring.chapter2.utils;

import com.google.common.base.Strings;
import com.mysql.cj.util.StringUtils;

/**
 * <p>
 * 数据类型转换 工具类
 * </p>
 *
 * @author Major Tom
 * @date 2023/6/13 12:12
 **/
public class CastUtil {
    /**
     * 转为String
     *
     * @param obj 待转换的对象
     * @return 转换后结果
     */
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    /**
     * 转换为String(提供默认值)
     *
     * @param obj          待转换的对象
     * @param defaultValue 默认值
     * @return 转换后的结果
     */
    public static String castString(Object obj, String defaultValue) {
        return null != obj ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为double
     *
     * @param obj 待转换的对象
     * @return 转换后结果
     */
    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    /**
     * 转换为double提供默认值)
     *
     * @param obj          待转换的对象
     * @param defaultValue 默认值
     * @return 转换后的结果
     */
    public static double castDouble(Object obj, double defaultValue) {
        double value = defaultValue;
        if (null != obj) {
            String strValue = castString(obj);
            if (!Strings.isNullOrEmpty(strValue)) {
                value = Double.parseDouble(strValue);
            }
        }
        return value;
    }

    /**
     * 转为long
     *
     * @param obj 待转换的对象
     * @return 转换后结果
     */
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    /**
     * 转换为long提供默认值)
     *
     * @param obj          待转换的对象
     * @param defaultValue 默认值
     * @return 转换后的结果
     */
    public static long castLong(Object obj, long defaultValue) {
        long value = defaultValue;
        if (null != obj) {
            String strValue = castString(obj);
            if (!Strings.isNullOrEmpty(strValue)) {
                value = Long.parseLong(strValue);
            }
        }
        return value;
    }

    /**
     * 转为int
     *
     * @param obj 待转换的对象
     * @return 转换后结果
     */
    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    /**
     * 转换为int(提供默认值)
     *
     * @param obj          待转换的对象
     * @param defaultValue 默认值
     * @return 转换后的结果
     */
    public static int castInt(Object obj, int defaultValue) {
        int value = defaultValue;
        if (null != obj) {
            String strValue = castString(obj);
            if (!Strings.isNullOrEmpty(strValue)) {
                value = Integer.parseInt(strValue);
            }
        }
        return value;
    }

    /**
     * 转为boolean
     *
     * @param obj 待转换的对象
     * @return 转换后结果
     */
    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    /**
     * 转换为boolean(提供默认值)
     *
     * @param obj          待转换的对象
     * @param defaultValue 默认值
     * @return 转换后的结果
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean value = defaultValue;
        if (null != obj) {
            value = Boolean.parseBoolean(castString(obj));
        }
        return value;
    }
}
