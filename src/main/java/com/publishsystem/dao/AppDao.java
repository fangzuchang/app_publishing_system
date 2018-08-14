/**
 *
 */
package com.publishsystem.dao;

import com.publishsystem.util.jdbc.TxQueryRunner;
import com.publishsystem.po.App;
import com.publishsystem.vo.Download;
import com.publishsystem.vo.History;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fzc69 2017年7月10日
 */
public class AppDao {
    private static final Logger logger = LoggerFactory.getLogger(AppDao.class);
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 查询客户所有版本app
     *
     * @param custId
     * @return
     */
    public List<App> queryAllAppByCust(Long custId) throws SQLException {
        String sql = "SELECT app.app_id,app.app_version,app.android_url,app.iphone_url,app.qrcode,app.description,app.iphPackageName,app.iphStoreUrl,app.createTimeDate,app.updateTime"
                + " FROM app"
                + " WHERE custId = ?"
                + " ORDER BY app_version DESC";
        List<Object> params = new ArrayList<Object>();
        params.add(custId);
        List<App> beanList = qr.query(sql, new BeanListHandler<App>(App.class), params.toArray());
        return beanList;
    }

    /**
     * 新增app
     *
     * @param app
     * @return
     */
    public void save(App app) throws SQLException {
        String sql = "insert into app(app_id,custId,app_version,description,createTimeDate,qrcode,android_url,iphone_url,plistUrl,iphPackageName,iphStoreUrl,publishStyle) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {app.getApp_id(), app.getCustId(), app.getApp_version(), app.getDescription(), app.getCreateTimeDate(), app.getQrcode(), app.getAndroid_url(), app.getIphone_url(), app.getPlistUrl(), app.getIphPackageName(), app.getIphStoreUrl(), app.getPublishStyle()};
        qr.update(sql, params);
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
    public void deleteAppsByIds(String[] idArray) throws SQLException {
        String whereSql = toWhereSql(idArray.length);
        String sql = "delete from app where app_id " + whereSql;
        qr.update(sql, idArray);
    }

    /**
     *
     */
    public Download getDownloadByCustId(Long custId) throws SQLException {
        String sql = "SELECT cu.id,cu.logoUrl,cu.appLogoUrl,ap.app_version,ap.description,ap.android_url,ap.android_url,ap.plistUrl,ap.iphStoreUrl"
                + " FROM customer as cu"
                + " LEFT JOIN app as ap"
                + " ON cu.id = ap.custId"
                + " WHERE cu.id = ? ORDER BY ap.app_version DESC";
        List<Download> beanList = qr.query(sql, new BeanListHandler<Download>(Download.class), custId);
        return beanList.get(0);
    }

    /**
     *
     */
    public List<History> getHistoryByCustId(Long custId) throws SQLException {
        String sql = "SELECT cu.id as custId,cu.logoUrl,ap.description as appDescription,ap.app_version as appVersion"
                + " FROM customer as cu"
                + " LEFT JOIN app as ap"
                + " ON cu.id = ap.custId"
                + " WHERE cu.id = ? ORDER BY ap.app_version DESC";
        List<History> beanList = qr.query(sql, new BeanListHandler<History>(History.class), custId);
        return beanList;
    }

    public Download getDownByCustIdAndAppVersion(Long custId, String appVersion) throws SQLException {
        String sql = "SELECT cu.id,cu.logoUrl,cu.appLogoUrl,ap.app_version,ap.description,ap.android_url,ap.android_url,ap.plistUrl,ap.iphStoreUrl"
                + " FROM customer as cu"
                + " LEFT JOIN app as ap"
                + " ON cu.id = ap.custId"
                + " WHERE cu.id = ? AND ap.app_version =?";
        return qr.query(sql, new BeanHandler<Download>(Download.class), custId, appVersion);
    }

    /**
     * @param id
     */
    public void deleteAppById(Long id) throws SQLException {
        String sql = new String("delete from app where app_id =?");
        qr.update(sql, id);
    }

    private String toWhereSql1(App app, List<Object> paramsList) {
        StringBuffer sql = new StringBuffer("app_version=?,description=? ,updateTime=?");
        paramsList.add(app.getApp_version());
        paramsList.add(app.getDescription());
        paramsList.add(app.getUpdateTime());
        if (app.getAndroid_url() != "") {
            sql.append(",android_url=?");
            paramsList.add(app.getAndroid_url());
        }
        if (app.getPublishStyle().equals("1")) {
            if (app.getIphStoreUrl() != "") {
                sql.append(",iphStoreUrl=?,publishStyle=?");
                paramsList.add(app.getIphone_url());
                paramsList.add(app.getPublishStyle());
            }
        } else {
            if (app.getIphone_url() != "") {
                sql.append(",iphone_url=?,iph_package_name=?,publishStyle=?");
                paramsList.add(app.getIphone_url());
                paramsList.add(app.getIphPackageName());
                paramsList.add(app.getPublishStyle());
            }
        }
        sql.append(" where app_id=?");
        paramsList.add(app.getApp_id());
        return sql.toString();
    }

    /**
     * @param app
     */
    public void update(App app) throws SQLException {
        List<Object> paramsList = new ArrayList<Object>();
        StringBuffer sql1 = new StringBuffer(
                "update app set " + toWhereSql1(app, paramsList));
        Object[] params = new Object[paramsList.size()];
        qr.update(sql1.toString(), paramsList.toArray());
    }

    public List<Long> queryAppIdsByCustId(Long custId) throws SQLException {
        String sql = "SELECT app.app_id FROM customer"
                + " LEFT JOIN app on customer.id=app.custId"
                + " WHERE customer.id=?";
        List<Long> beanList = qr.query(sql, new BeanListHandler<Long>(Long.class), custId);
        return beanList;
    }

}
