package com.publishsystem.service;

import com.publishsystem.dao.AdminDao;
import com.publishsystem.po.Administrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * @author fzc
 * @date 2018-5-13 12:29
 */
public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private AdminDao adminDao = new AdminDao();

    /**
     * 根据用户名密码查询管理员
     *
     * @param param
     * @return
     */
    public Administrator queryUser(Administrator param) {
        try {
            return adminDao.queryUser(param);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存管理员
     *
     * @param admin
     */
    public void save(Administrator admin) {
        try {
            adminDao.save(admin);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 更新用户的信息
     *
     * @param param
     */
    public void update(Administrator param) {
        try {
            adminDao.update(param);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
