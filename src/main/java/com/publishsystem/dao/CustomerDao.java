/**
 *
 */
package com.publishsystem.dao;

import com.publishsystem.util.jdbc.TxQueryRunner;
import com.publishsystem.po.Customer;
import com.publishsystem.vo.AppAndCustomer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fzc69 2017年7月12日
 */
public class CustomerDao {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDao.class);
    private QueryRunner qr = new TxQueryRunner();


    public Customer queryAppLogoUrlByCustId(Long custId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE id =?";
        return qr.query(sql, new BeanHandler<Customer>(Customer.class), custId);
    }

    /**
     * 查询客户和app
     *
     * @return
     */
    public List<AppAndCustomer> queryAllCustAndApp() throws SQLException {
        String sql = "SELECT cu.id,cu.custName,cu.logoUrl,ap.app_version,ap.description,cu.appLogoUrl,ap.iphStoreUrl"
                + " FROM customer as cu"
                + " LEFT JOIN app as ap on cu.id = ap.custId"
                + " GROUP BY cu.id";
        List<AppAndCustomer> beanList = qr.query(sql, new BeanListHandler<AppAndCustomer>(AppAndCustomer.class));
        return beanList;
    }

    /**
     * 查询所有客户
     *
     * @return
     */
    public List<Customer> queryAllCust() throws SQLException {
        String sql = "SELECT * FROM customer";
        List<Customer> beanList = qr.query(sql, new BeanListHandler<Customer>(Customer.class));
        return beanList;
    }

    /**
     * 保存
     *
     * @param customer
     * @return
     */
    public boolean save(Customer customer) throws SQLException {
        String sql = "insert into customer(id,custName,logoUrl,appLogoUrl,createTime) values(?,?,?,?,?)";
        Object[] params = {customer.getId(), customer.getCustName(), customer.getLogoUrl(), customer.getAppLogoUrl(), customer.getCreateTime()};
        return qr.update(sql, params) == 0 ? false : true;
    }

    /**
     * 根据id删除客户的信息
     *
     * @param id
     */
    public void deleteCustById(Long id) throws SQLException {
        String sql = "DELETE customer,app FROM customer LEFT JOIN app ON customer.id=app.custId WHERE customer.id=?";
        qr.update(sql, id);
    }

    /*
         * 用来生成where子句
         */
    private String toWhereSql(int len) {
        StringBuilder sb = new StringBuilder(" in(");
        for (int i = 0; i < len; i++) {
            sb.append("?");
            if (i < len - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 批量删除
     */
    public void deleteCustsByIds(String[] idArray) throws SQLException {
        String whereSql = toWhereSql(idArray.length);
        String sql = "delete from Customer where " + whereSql;
        qr.update(sql, idArray);
    }

    /**
     * 更新
     *
     * @param param
     */
    public void update(Customer param) throws SQLException {
        List<Object> paramsList = new ArrayList<Object>();
        StringBuffer sql1 = new StringBuffer("UPDATE Customer set " + toWhereSql1(param, paramsList));
        qr.update(sql1.toString(), paramsList.toArray());
    }

    private String toWhereSql1(Customer customer, List<Object> paramsList) {
        StringBuffer sql = new StringBuffer("custName=?,updateTime=?");
        paramsList.add(customer.getCustName());
        paramsList.add(customer.getUpdateTime());
        if (customer.getLogoUrl() != "") {
            sql.append(",logoUrl=?");
            paramsList.add(customer.getLogoUrl());
        }
        if (customer.getAppLogoUrl() != "") {
            sql.append(",appLogoUrl=?");
            paramsList.add(customer.getAppLogoUrl());
        }
        sql.append(" where id=?");
        paramsList.add(customer.getId());
        return sql.toString();
    }

}
