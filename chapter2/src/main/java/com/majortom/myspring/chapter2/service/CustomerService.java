package com.majortom.myspring.chapter2.service;

import com.google.common.collect.Lists;
import com.majortom.myspring.chapter2.model.Customer;
import com.majortom.myspring.chapter2.utils.PropUtil;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * 客户数据服务
 * </p>
 *
 * @author Major Tom
 * @date 2023/6/12 23:10
 **/
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties prop = PropUtil.loadProperties("config.properties");
        DRIVER = prop.getProperty("jdbc.driver");
        URL = prop.getProperty("jdbc.url");
        USERNAME = prop.getProperty("jdbc.username");
        PASSWORD = prop.getProperty("jdbc.password");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Could not load jdbc driver", e);
        }
    }

    /**
     * 获取客户列表
     *
     * @return 客户列表
     */
    public List<Customer> getCustomerList() {
        List<Customer> customerList = Lists.newArrayList();
        String sql = "SELECT * FROM consumer";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = Customer.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .contact(rs.getString("contact"))
                        .telephone(rs.getString("telephone"))
                        .email(rs.getString("email"))
                        .remark(rs.getString("remark"))
                        .build();
                customerList.add(customer);
            }
        } catch (SQLException e) {
            LOGGER.error("Execute sql failure", e);
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("Close connection failure", e);
                }
            }
        }
        return customerList;
    }

    /**
     * 获取客户
     *
     * @param id 客户ID
     * @return 客户
     */
    public Customer getCustomer(Long id) {
        return null;
    }

    /**
     * 新建客户
     *
     * @param fieldMap 客户信息
     * @return 是否成功
     */
    public boolean createCustomer(Map<String, Object> fieldMap) {
        return true;
    }

    /**
     * 更新客户信息
     *
     * @param id       客户ID
     * @param fieldMap 客户信息
     * @return 是否成功
     */
    public boolean updateCustomer(Long id, Map<String, Object> fieldMap) {
        return true;
    }

    /**
     * 删除客户
     *
     * @param id 客户ID
     * @return 是否成功
     */
    public boolean deleteCustomer(Long id) {
        return true;
    }

}
