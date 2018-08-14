package com.publishsystem.action;

import com.publishsystem.util.BaseServlet;
import com.publishsystem.po.App;
import com.publishsystem.po.Customer;
import com.publishsystem.service.AppService;
import com.publishsystem.service.CustomerService;
import com.publishsystem.util.FileUtil;
import com.publishsystem.util.JsonUtil;
import com.publishsystem.util.QRUtil;
import com.publishsystem.vo.Download;
import com.publishsystem.vo.History;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@WebServlet(name = "AppAction")
public class AppAction extends BaseServlet {
    private static final Logger logger = LoggerFactory.getLogger(AppAction.class);
    AppService appService = new AppService();
    CustomerService customerService = new CustomerService();

    /**
     * 通过id查询所有app
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String queryApp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String custIdStr = request.getParameter("custId");
        List<App> appList = appService.queryAllAppByCust(Long.parseLong(custIdStr));
        request.getSession().setAttribute("appList", appList);
        return "f:/appManage.jsp";
        /*Map<String, Object> datas = new HashMap<String, Object>();
        String custIdStr = request.getParameter("custId");
        String returnJsonData = "";
        AppAndCustomerDao dao = new AppAndCustomerDao();
        try {
            List<App> appList = dao.queryAllAppByCust(Long.parseLong(custIdStr));
            datas.put("result", "success");
            datas.put("data", appList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            datas.put("result", "failed");
            datas.put("msg", e.toString());
        }
        returnJsonData = JsonUtil.mapToJson(datas);
        //appid数据长度超出js最大显示，转换为字符串形式
        returnJsonData = returnJsonData.replaceAll("app_id\":", "app_id\":\"").replaceAll(",\"custId\":", "\",\"custId\":\"").replaceAll(",\"app_version", "\",\"app_version");
        JsonUtil.writeJson(response, returnJsonData);
        return null;*/
    }

    /**
     * 批量删除app
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */

    public String delApps(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> datas = new HashMap<String, Object>();
        String returnJsonData = "";
        String appIds = request.getParameter("ids");
        String[] appIdArray = appIds.split("@fzc@");
        if (appService.deleteAppsByIds(appIdArray)) {//删除记录
            datas.put("result", "true");
            datas.put("msg", "删除成功！");
            for (int i = 0; i < appIdArray.length; i++) {//删除安装包
                String savePath = request.getServletContext().getRealPath("/upload/app/" + appIdArray[i].toString());
                FileUtil.deleteDirectory(savePath);
            }
        } else {
            datas.put("result", "false");
            datas.put("msg", "删除失败！");
        }
        JsonUtil.writeJson(response, JsonUtil.mapToJson(datas));
        return "";
    }

    /**
     * 编辑app
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> datas = new HashMap<String, Object>();
        try {
            if (updateApp(request)) {
                datas.put("result", "true");
            } else {
                datas.put("result", "false");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            datas.put("result", "false");
            datas.put("message", e.toString());
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JsonUtil.mapToJson(datas));
        return "";
    }

    private Boolean updateApp(HttpServletRequest request) throws Exception {
        String appVersion = "";
        String appDescription = "";
        String iphPackageName = "";
        Long custId = 0L;
        String publishStyle = "";
        String iphStoreUrl = "";//苹果商店发布
        Long appId = 0L;

        String andUrl = "";
        String iphUrl = "";
        String plistUrl = "";
        String savePath = "";
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        if (!ServletFileUpload.isMultipartContent(request)) {
            return null;
        }
        List<FileItem> list = upload.parseRequest(request);
        for (FileItem item : list) {
            // 如果fileitem中封装的是普通输入项的数据
            if (item.isFormField()) {
                String name = item.getFieldName();
                //解决普通输入项的数据的中文乱码问题
                String value = item.getString("UTF-8");
                if (name.equals("app_version1")) {
                    appVersion = value;
                } else if (name.equals("app_description1")) {
                    appDescription = value;
                } else if (name.equals("iph_package_name1")) {
                    iphPackageName = value;
                } else if (name.equals("custId")) {
                    custId = Long.parseLong(value);
                } else if (name.equals("pub_style1")) {
                    publishStyle = value;
                } else if (name.equals("iph_store_url1")) {
                    iphStoreUrl = value;
                } else if (name.equals("appId")) {
                    appId = Long.parseLong(value);
                    savePath = request.getServletContext().getRealPath(
                            "/upload/app/" + appId.toString());
                    File saveFile = new File(savePath);
                    if (!saveFile.exists()) {
                        saveFile.mkdirs();
                    }
                }
            } else {// 如果fileitem中封装的是上传文件
                // 得到上传的文件名称，
                String filePath = item.getName();
                if (filePath == null || filePath.trim().equals("")) {
                    continue;
                }
                //文件名
                String filename = filePath.substring(filePath
                        .lastIndexOf("\\") + 1);
                //文件后缀
                String suffix = filename.substring(filename
                        .lastIndexOf("."));
                if ("and_app1".equals(item.getFieldName())) {//安卓端
                    //新文件名
                    filename = "android_" + appVersion + "_" + suffix;
                    //服务器保存地址
                    andUrl = "/upload/app/" + appId.toString() + "/"
                            + filename;
                }
                if ("iph_app1".equals(item.getFieldName())) {//企业发布
                    //新文件名
                    filename = "iphone_" + appVersion + "_" + suffix;
                    //服务器保存地址
                    iphUrl = "/upload/app/" + appId.toString() + "/"
                            + filename;
                    plistUrl = "/upload/app/" + appId.toString()
                            + "/" + appId.toString() + "_" + appVersion
                            + ".plist";
                    // 生成plist文件并保存plist文件路径
                    String plistPath = request.getServletContext().getRealPath("/upload/app/" + appId.toString()) + "\\";
                    String plistName = appId.toString() + "_"
                            + appVersion + ".plist";
                    // 软件logo地址
                    Customer customer = customerService.queryCustomerByCustId(custId);
                    String appLogoUrl = customer.getAppLogoUrl();
                    //服务器地址
                    String host = request.getServletContext().getInitParameter("host");
                    FileUtil.createPlist(host, customer.getCustName(), iphUrl, appLogoUrl, iphPackageName, plistPath, plistName);
                }
                String storePath = savePath + File.separator + filename;
                item.write(new File(storePath));
                // 删除处理文件上传时生成的临时文件
                item.delete();
            }
        }
        if (publishStyle.equals("appstore")) {
            publishStyle = "1";
        } else {
            publishStyle = "2";
        }
        Date date = new Date();
        //修改时间
        Timestamp timeStamep = new Timestamp(date.getTime());
        App app = new App(appId, appVersion, andUrl, iphUrl, appDescription, timeStamep, iphPackageName, iphStoreUrl, publishStyle, plistUrl);
        return appService.update(app);
    }

    /**
     * 保存app
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String save(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> datas = new HashMap<String, Object>();
        String message = "";
        try {
            if (saveApp(request)) {
                datas.put("result", "true");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message = "false";
            datas.put("result", e.toString());
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JsonUtil.mapToJson(datas));
        return "";
    }

    private Boolean saveApp(HttpServletRequest request) throws Exception {
        String appVersion = "";
        String appDescription = "";
        Long custId = 0L;
        String iphPackageName = "";
        String publishStyle = "";
        String iphStoreUrl = "";//苹果商店发布

        String andUrl = "";
        String iphUrl = "";
        String plistUrl = "";
        Long appId = UUID.randomUUID().getMostSignificantBits();
        String savePath = request.getServletContext().getRealPath(
                "/upload/app/" + appId.toString());
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        //服务器地址
        String host = request.getServletContext().getInitParameter("host");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        if (!ServletFileUpload.isMultipartContent(request)) {
            return null;
        }
        List<FileItem> list = upload.parseRequest(request);
        for (FileItem item : list) {
            String value = item.getString("UTF-8");
            String name = item.getFieldName();
            if (item.isFormField()) {
                if (name.equals("app_version")) {
                    appVersion = value;
                } else if (name.equals("app_description")) {
                    appDescription = value;
                } else if (name.equals("iph_package_name")) {
                    iphPackageName = value;
                } else if (name.equals("custId")) {
                    custId = Long.parseLong(value);
                } else if (name.equals("pub_style")) {
                    publishStyle = value;
                } else if (name.equals("iph_store_url")) {
                    iphStoreUrl = value;
                }
            } else {// 如果fileitem中封装的是上传文件
                // 得到上传的文件名称，
                String filePath = item.getName();
                if (filePath == null || filePath.trim().equals("")) {
                    continue;
                }
                String filename = filePath.substring(filePath
                        .lastIndexOf("\\") + 1);
                String suffix = filename.substring(filename
                        .lastIndexOf("."));
                if ("and_app".equals(name)) {
                    filename = "android_" + appVersion + "_" + suffix;
                    andUrl = "/upload/app/" + appId.toString() + "/"
                            + filename;
                } else if ("iph_app".equals(name)) {
                    filename = "iphone_" + appVersion + "_" + suffix;
                    iphUrl = "/upload/app/" + appId.toString() + "/"
                            + filename;
                    // 生成plist文件并保存plist文件路径
                    String plistPath = request
                            .getServletContext()
                            .getRealPath("/upload/app/" + appId.toString()) + File.separator;
                    plistUrl = "/upload/app/" + appId.toString()
                            + "/" + appId.toString() + "_" + appVersion
                            + ".plist";
                    String plistName = appId.toString() + "_"
                            + appVersion + ".plist";
                    // 软件logo地址
                    Customer customer = customerService.queryCustomerByCustId(custId);
                    String appLogoUrl = customer.getAppLogoUrl();
                    FileUtil.createPlist(host, customer.getCustName(), iphUrl, appLogoUrl, iphPackageName, plistPath, plistName);
                }
                String storePath = savePath + File.separator + filename;
                item.write(new File(storePath));
                // 删除处理文件上传时生成的临时文件
                item.delete();
            }
        }
        String qrCodeContent = host + "/appAction?method=hisToDown&appVersion="
                + appVersion + "&custId=" + custId;
        String qrFileName = appId + "_" + appVersion + "_qrcode.jpg";
        // 保存路径
        String saveQrPath = savePath + File.separator + qrFileName;
        // 生成二维码
        QRUtil.createQrCode(qrCodeContent, "jpg", 300, 300, saveQrPath);
        // 二维码位置
        String qrCodeUrl = "upload/app/" + appId.toString() + "/" + qrFileName;
        if (!iphStoreUrl.equals("") || !iphUrl.equals("")) {
            if (publishStyle.equals("appstore")) {
                publishStyle = "1";
            } else {
                publishStyle = "2";
            }
        } else {
            publishStyle = "";
        }
        Date date = new Date();
        Timestamp timeStamep = new Timestamp(date.getTime());
        App app = new App(appId, custId, appVersion, andUrl, iphUrl,
                "", appDescription, timeStamep, plistUrl,
                iphPackageName, qrCodeUrl, iphStoreUrl, publishStyle);
        return appService.save(app);
    }

    public String download(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String custId = request.getParameter("custId");
        if (custId != "" && custId != null) {
            try {
                Long custId1 = Long.parseLong(custId);
                //通过id查询下载地址，描述，logo地址，历史下载地址
                Download download = appService.getDownloadByCustId(custId1);
                if (download != null) {
                    //验证plist文件
                    /*if (download.getPlistUrl() != "") {
                        //服务器地址
                        String host = request.getServletContext().getInitParameter("host");
                        String plistPath = request
                            .getServletContext()
                            .getRealPath("/upload/app/" + appId.toString()) + File.separator;
                        String plistName = appId.toString() + "_"
                            + download.getApp_version() + ".plist";
                        FileUtil.createPlist(host, customer.getCustName(), download.getIphone_url(), download.getAppLogoUrl(), iphPackageName, plistPath, plistName);
                    }*/
                    //生成plist
                    request.getSession().setAttribute("download", download);
                    return "f:/download.jsp";
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return "r:/index.jsp";
    }

    public String history(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String custId = request.getParameter("custId");
        if (custId != "") {
            try {
                Long custId1 = Long.parseLong(custId);
                //通过id查询下载地址，描述，logo地址，历史下载地址
                List<History> history = appService.getHistoryByCustId(custId1);
                // 1、先获取session对象
                HttpSession session = request.getSession();
                session.setAttribute("historyList", history);
                return "f:history.jsp";
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                response.sendRedirect("index.jsp");
            }
        } else {
            response.sendRedirect("index.jsp");
        }
        return null;
    }

    public String hisToDown(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String custId = request.getParameter("custId");
        String appVersion = request.getParameter("appVersion");
        if (custId != "" && appVersion != "") {
            try {
                Long custId1 = Long.parseLong(custId);
                Download download = appService.getDownByCustIdAndAppVersion(custId1, appVersion);
                if (download != null) {
                    request.getSession().setAttribute("download", download);
                    return "f:download.jsp";
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return "r:/index.jsp";
    }

}
