package com.publishsystem.dao;

import com.publishsystem.util.jdbc.TxQueryRunner;
import com.publishsystem.po.Administrator;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * @author fzc69 2017年7月10日
 */
public class AdminDao {
    private static final Logger logger = LoggerFactory.getLogger(AdminDao.class);
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 根据用户名密码查询管理员
     *
     * @param param
     * @return
     */
    public Administrator queryUser(Administrator param) throws SQLException {
        String sql = "select * from administrator where name=? and password=?";
        return qr.query(sql, new BeanHandler<Administrator>(Administrator.class), param.getName(), param.getPassWord());
    }

    /**
     * 保存
     *
     * @param admin
     */
    public void save(Administrator admin) throws SQLException {
        String sql = "insert into users(name,realName,password) values(?,?,?)";
        Object[] params = {admin.getName(), admin.getRealName(), admin.getPassWord()};
        qr.update(sql, params);
    }

    /**
     * 更新
     *
     * @param param
     */
    public void update(Administrator param) throws SQLException {
        String sql = "update administrator set password=?,name=? where id=?";
        Object[] params = {param.getPassWord(), param.getName(), param.getId()};
        qr.update(sql, param);
    }

}
