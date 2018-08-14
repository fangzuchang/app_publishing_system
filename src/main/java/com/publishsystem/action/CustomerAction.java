package com.publishsystem.action;

import com.publishsystem.util.BaseServlet;
import com.publishsystem.po.Customer;
import com.publishsystem.service.AppService;
import com.publishsystem.service.CustomerService;
import com.publishsystem.util.ChineseCharacterUtil;
import com.publishsystem.util.FileUtil;
import com.publishsystem.util.JsonUtil;
import com.publishsystem.vo.AppAndCustomer;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author fzc69 2017年7月10日
 */
public class CustomerAction extends BaseServlet {
    private static final Logger logger = LoggerFactory.getLogger(CustomerAction.class);
    CustomerService customerService = new CustomerService();
    AppService appService = new AppService();

    public String addCust(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long custId = UUID.randomUUID().getMostSignificantBits();
        String savePath = request.getServletContext().getRealPath(
                "/upload/logo/" + custId.toString());
// 创建保存目录的文件
        File saveFile = new File(savePath);
        // 判断保存目录文件是否存在,不存在则创建一个文件夹
        if (!saveFile.exists()) {
            System.out.println("文件目录创建中。。。");
            saveFile.mkdirs();
        }
        String custName = "";
        // 客户名缩写
        String custSimpleName = "";
        String logoUrl = "";
        String appLogoUrl = "";


        // 消息提示
        String message = "";
        // 使用Apache文件上传组件处理文件上传步骤：
        try {
            // 1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 2.创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            // 3.判断提交上来的数据是否是上传表单的数据
            if (!ServletFileUpload.isMultipartContent(request)) {
                // 按照传统方式获取数据
                return null;
            }
            // 4.使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<fileItem>集合，
            // 每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
                /* for(FileItem item: list){ */
            for (FileItem item : list) {
                // 如果fileitem中封装的是普通输入项的数据
                if (item.isFormField()) {
                    String value = item.getString("UTF-8");
                    if ("cust_name".equals(item.getFieldName())) {
                        custName = value;
                        custSimpleName = ChineseCharacterUtil
                                .convertHanzi2Pinyin(value, false);
                    }
                } else {// 如果fileitem中封装的是上传文件
                    // 得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    String suffix = filename.substring(filename
                            .lastIndexOf("."));
                    if ("cust_logo".equals(item.getFieldName())) {
                        filename = custSimpleName + "_logo" + suffix;
                        logoUrl = "upload/logo/" + custId + "/"
                                + filename;
                    }
                    if ("app_logo".equals(item.getFieldName())) {
                        filename = custSimpleName + "_logo_app" + suffix;
                        appLogoUrl = "upload/logo/" + custId + "/"
                                + filename;
                    }
                    // 获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    // 创建一个文件输出流

                    FileOutputStream out = new FileOutputStream(savePath
                            + "\\" + filename);
                    // 创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    // 判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    // 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while ((len = in.read(buffer)) > 0) {
                        // 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath +
                        // "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    // 关闭输入流
                    in.close();
                    // 关闭输出流
                    out.close();
                    // 删除处理文件上传时生成的临时文件
                    item.delete();
                }
            }
            message = "新建成功！";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message = "新建失败!";
        }
        Customer customer = new Customer(custId, custName, logoUrl,
                appLogoUrl, new java.sql.Timestamp((new Date()).getTime()));
        customerService.save(customer);
        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("result", "true");
        datas.put("message", message);
        JsonUtil.writeJson(response, JsonUtil.mapToJson(datas));
        return null;
    }

    public String deleteCust(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> datas = new HashMap<String, Object>();
        // 获取提交的id的值
        String param = request.getParameter("custId");
        Long id = Long.valueOf(param);
        try {
            //删除次客户logo文件
            String logoPath = request.getServletContext().getRealPath(
                    "/upload/logo/" + param);
            // 删除安装包文件
            FileUtil.deleteDirectory(logoPath);
            // 查询此客户所有appid
            List<Long> appIdList = appService.queryAppIdsByCustId(id);
            //删除此客户所有app安装包
            if (appIdList != null) {
                for (int i = 0; i < appIdList.size(); i++) {
                    String savePath = request.getServletContext().getRealPath(
                            "/upload/app/" + appIdList.get(i).toString());
                    // 删除安装包文件
                    FileUtil.deleteDirectory(savePath);
                }
            }
            // 删除数据库中数据
            customerService.deleteCustById(id);
            datas.put("result", "true");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            datas.put("result", "false");
            datas.put("msg", e.getMessage());
        }
        JsonUtil.writeJson(response, JsonUtil.mapToJson(datas));
        return null;
    }

    public String delCusts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> datas = new HashMap<String, Object>();
        String ids = request.getParameter("ids");
        String[] idArray = ids.split("@fzc@");
        // 删除客户资料
        try {
            for (int i = 0; i < idArray.length; i++) {
                Long id = Long.parseLong(idArray[i]);
                String logoPath = request.getServletContext().getRealPath(
                        "/upload/logo/" + id.toString());
                FileUtil.deleteDirectory(logoPath);
                List<Long> appIdList = appService.queryAppIdsByCustId(id);
                //删除此客户所有app安装包
                for (int j = 0; j < appIdList.size(); j++) {
                    String savePath = request.getServletContext().getRealPath(
                            "/upload/app/" + appIdList.get(j).toString());
                    FileUtil.deleteDirectory(savePath);
                }
            }
            customerService.deleteCustsByIds(idArray);
            datas.put("result", "true");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            datas.put("result", "false");
            datas.put("msg", e.getMessage());
        }
        String returnJsonData = JsonUtil.mapToJson(datas);
        JsonUtil.writeJson(response, returnJsonData);
        return null;
    }

    public String editCust(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> datas = new HashMap<String, Object>();
        String custName = "";
        String custSimpleName = "";
        String logoUrl = "";
        String appLogoUrl = "";
        Long custId = 0L;
        String savePath = "";
        File saveFile = null;
        String message = "";
        // 使用Apache文件上传组件处理文件上传步骤：
        try {
            // 1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 2.创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            // 3.判断提交上来的数据是否是上传表单的数据
            if (!ServletFileUpload.isMultipartContent(request)) {
                // 按照传统方式获取数据
                return null;
            }
            // 4.使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<fileItem>集合，
            // 每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                // 如果fileitem中封装的是普通输入项的数据
                if (item.isFormField()) {
                    String value = item.getString("UTF-8");
                    if ("cust_name1".equals(item.getFieldName())) {
                        custName = value;
                        custSimpleName = ChineseCharacterUtil
                                .convertHanzi2Pinyin(value, false);
                    }
                    if ("custId".equals(item.getFieldName())) {
                        custId = Long.parseLong(value);
                        savePath = request.getServletContext().getRealPath(
                                "/upload/logo/" + custId.toString());
                        // 创建保存目录的文件
                        saveFile = new File(savePath);
                        // 判断保存目录文件是否存在,不存在则创建一个文件夹
                        if (!saveFile.exists()) {
                            System.out.println("文件目录创建中。。。");
                            saveFile.mkdirs();
                        }
                    }
                } else {// 如果fileitem中封装的是上传文件
                    // 得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    String suffix = filename.substring(filename
                            .lastIndexOf("."));
                    if ("cust_logo1".equals(item.getFieldName())) {
                        filename = custSimpleName + "_logo" + suffix;
                        logoUrl = "upload/logo/" + custId + "/"
                                + filename;
                    }
                    if ("app_logo1".equals(item.getFieldName())) {
                        filename = custSimpleName + "_logo_app" + suffix;
                        appLogoUrl = "upload/logo/" + custId + "/"
                                + filename;
                    }
                    // 获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    // 创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath
                            + "\\" + filename);
                    // 创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    // 判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    // 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while ((len = in.read(buffer)) > 0) {
                        // 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath +
                        // "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    // 关闭输入流
                    in.close();
                    // 关闭输出流
                    out.close();
                    // 删除处理文件上传时生成的临时文件
                    item.delete();
                }
            }
            Customer customer = new Customer(custId, custName, logoUrl,
                    new java.sql.Timestamp((new Date()).getTime()), appLogoUrl);
            // 更新操作
            if (customerService.update(customer)) {
                datas.put("result", "true");
            } else {
                datas.put("result", "true");
                datas.put("msg", "修改失败！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            datas.put("result", "false");
            datas.put("msg", "修改失败！");
        }
        String returnJsonData = JsonUtil.mapToJson(datas);
        JsonUtil.writeJson(response, returnJsonData);
        return null;
    }

    public String queryAllCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Customer> custList = customerService.queryAllCust();
        request.getSession().setAttribute("custList", custList);
        return "f:/custManage.jsp";
    }

    public String queryAllCustAndApp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<AppAndCustomer> appAndCustomerList = null;
        appAndCustomerList = customerService.queryAllCustAndApp();
        // 判断用户有没有
        if (appAndCustomerList != null) {
            request.getSession().setAttribute("appAndCustomerList", appAndCustomerList);
            return "f:custManage.jsp";
        } else {
            // 提示
            // 1、先将提示信息request
            request.setAttribute("error", "登录失败！用户名或密码错误！");
            // 2、重新返回登录界面-->给用户提示
            return "f:login.jsp";
        }
    }

}
