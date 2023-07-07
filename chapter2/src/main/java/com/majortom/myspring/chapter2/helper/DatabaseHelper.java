package com.majortom.myspring.chapter2.helper;

import com.google.common.collect.Lists;
import com.majortom.myspring.chapter2.utils.PropUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * 数据库连接操作助手
 * </p>
 *
 * @author Major Tom
 * @date 2023/6/13 23:07
 **/
public final class DatabaseHelper {
    /**
     * 日志工具
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    /**
     * 数据库连接池
     */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    /**
     * 查询解析器
     */
    private static final QueryRunner QUERY_RUNNER;

    /**
     * 数据源
     */
    private static final BasicDataSource DATA_SOURCE;


    static {
        //初始化数据库连接池
        CONNECTION_HOLDER = new ThreadLocal<>();
        QUERY_RUNNER = new QueryRunner();

        Properties prop = PropUtil.loadProperties("config.properties");
        String driver = prop.getProperty("jdbc.driver");
        String url = prop.getProperty("jdbc.url");
        String userName = prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(userName);
        DATA_SOURCE.setPassword(password);
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     */
    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (null == conn) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("Get connection failure");
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }

        return conn;
    }

    /**
     * 查询实体列表
     * <T> 声明参数类型，即先把T给声明出来，不然编译器不认识
     * List<T> 返回集合，集合的元素为T类型
     *
     * @param entityClass 实体类Class
     * @param sql         查询sql
     * @param params      查询参数
     * @param <T>         实体类型
     * @return 实体列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("Query entityList failure", e);
            throw new RuntimeException(e);
        }
        return entityList;
    }

    /**
     * 查询实体
     *
     * @param entityClass 实体类Class
     * @param sql         查询sql
     * @param params      查询参数
     * @param <T>         实体类型
     * @return 实体
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("Query entityList failure", e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * 执行查询语句
     *
     * @param sql    查询sql
     * @param params 查询参数
     * @return 查询结果
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (Exception e) {
            LOGGER.error("Execute query failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 执行更新语句
     *
     * @param sql    更新sql
     * @param params 查询参数
     * @return 更新影响的行数
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (Exception e) {
            LOGGER.error("Execute update failure", e);
            throw new RuntimeException(e);
        }
        return rows;
    }

    /**
     * 插入实体
     *
     * @param entityClass /
     * @param fieldMap    /
     * @param <T>         /
     * @return /
     */
    public static <T> boolean insertEntry(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (MapUtils.isEmpty(fieldMap)) {
            LOGGER.error("Cannot insert entity:fieldMap is empty.");
            return false;
        }
        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " VALUES " + values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 更新实体
     *
     * @param entityClass 实体类
     * @param id          id
     * @param fieldMap    字段集合
     * @param <T>         类型
     * @return /
     */
    public static <T> boolean updateEntry(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (MapUtils.isEmpty(fieldMap)) {
            LOGGER.error("Cannot insert entity:fieldMap is empty.");
            return false;
        }
        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id=?";
        List<Object> paramList = Lists.newArrayList();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] paramArr = paramList.toArray();
        return executeUpdate(sql, paramArr) == 1;
    }

    public static <T> boolean deleteEntry(Class<T> entityClass, long id) {
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id=?";
        return executeUpdate(sql, id) == 1;
    }

    private static String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }

}
