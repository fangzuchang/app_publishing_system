package com.publishsystem.service;

import com.publishsystem.dao.CustomerDao;
import com.publishsystem.po.Customer;
import com.publishsystem.vo.AppAndCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * @author fzc
 * @date 2018-5-13 13:33
 */
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    CustomerDao customerDao = new CustomerDao();

    public Customer queryCustomerByCustId(Long custId) {
        try {
            return customerDao.queryAppLogoUrlByCustId(custId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 查询客户和app
     *
     * @return
     */
    public List<AppAndCustomer> queryAllCustAndApp() {
        try {
            return customerDao.queryAllCustAndApp();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    public List<Customer> queryAllCust() {
        try {
            return customerDao.queryAllCust();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 新增客户
     *
     * @param customer
     * @return
     */
    public boolean save(Customer customer) {
        try {
            return customerDao.save(customer);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 根据id删除客户的信息
     *
     * @param id
     */
    public void deleteCustById(Long id) {
        try {
            customerDao.deleteCustById(id);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 删除多个用户
     */
    public boolean deleteCustsByIds(String[] idArray) {
        try {
            customerDao.deleteCustsByIds(idArray);
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 修改客户信息
     *
     * @param param
     */
    public Boolean update(Customer param) {
        try {
            customerDao.update(param);
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }
}
