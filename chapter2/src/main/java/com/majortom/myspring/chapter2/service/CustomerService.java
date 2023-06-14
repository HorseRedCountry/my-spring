package com.majortom.myspring.chapter2.service;

import com.google.common.collect.Lists;
import com.majortom.myspring.chapter2.helper.DatabaseHelper;
import com.majortom.myspring.chapter2.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Map;

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


    /**
     * 获取客户列表
     *
     * @return 客户列表
     */
    public List<Customer> getCustomerList() {
        Connection conn = null;
        List<Customer> customerList = Lists.newArrayList();
        try {
            String sql = "SELECT * FROM consumer";
            conn = DatabaseHelper.getConnection();
            customerList = DatabaseHelper.queryEntityList(Customer.class, sql, conn);
        } finally {
            DatabaseHelper.closeConnection(conn);
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
