package com.publishsystem.service;

import com.publishsystem.dao.AppDao;
import com.publishsystem.po.App;
import com.publishsystem.vo.Download;
import com.publishsystem.vo.History;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * @author fzc
 * @date 2018-5-13 15:05
 */
public class AppService {
    private static final Logger logger = LoggerFactory.getLogger(AppService.class);
    AppDao appDao = new AppDao();

    /**
     * 查询客户所有版本app
     *
     * @param custId
     * @return
     */
    public List<App> queryAllAppByCust(Long custId) {
        try {
            return appDao.queryAllAppByCust(custId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 新增app
     *
     * @param app
     * @return
     */
    public boolean save(App app) {
        try {
            appDao.save(app);
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


    /**
     * 批量删除
     */
    public boolean deleteAppsByIds(String[] idArray) {
        try {
            appDao.deleteAppsByIds(idArray);
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     *
     */
    public Download getDownloadByCustId(Long custId) {
        try {
            return appDao.getDownloadByCustId(custId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     */
    public List<History> getHistoryByCustId(Long custId) {
        try {
            return appDao.getHistoryByCustId(custId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Download getDownByCustIdAndAppVersion(Long custId, String appVersion) {
        try {
            return appDao.getDownByCustIdAndAppVersion(custId, appVersion);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param id
     */
    public boolean deleteAppById(Long id) {
        try {
            appDao.deleteAppById(id);
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


    /**
     * @param app
     */
    public Boolean update(App app) {
        try {
            appDao.update(app);
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


    public List<Long> queryAppIdsByCustId(Long custId) {
        try {
            return appDao.queryAppIdsByCustId(custId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
