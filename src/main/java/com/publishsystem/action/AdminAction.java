/**
 *
 */
package com.publishsystem.action;

import com.publishsystem.util.BaseServlet;
import com.publishsystem.po.Administrator;
import com.publishsystem.service.AdminService;
import com.publishsystem.util.JsonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fzc69
 * 2017年7月10日
 */
public class AdminAction extends BaseServlet {
    private static final Logger logger = LoggerFactory.getLogger(AdminAction.class);
    private AdminService adminService = new AdminService();

    /**
     * 登录
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> datas = new HashMap<String, Object>();
        String captcha = request.getParameter("captcha");
        if (!request.getSession().getAttribute("code").equals(captcha)) {
            datas.put("result", "false");
            datas.put("msg", "验证码错误");
            JsonUtil.writeJson(response, JsonUtil.mapToJson(datas));
            return null;
        }
        String name = request.getParameter("username");
        String password = DigestUtils.md5Hex(request.getParameter("password"));
        // 将密码加密
        Administrator param = new Administrator();
        param.setName(name);
        param.setPassWord(password);
        // 查询数据库之后返回的user对象
        Administrator admin = adminService.queryUser(param);
        if (admin != null) {
            request.getSession().setAttribute("admin", admin);
            datas.put("result", "true");
        } else {
            datas.put("result", "false");
            datas.put("msg", "用户名或密码错误");
        }
        JsonUtil.writeJson(response, JsonUtil.mapToJson(datas));
        return null;
    }

    /**
     * 注销
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect("login.jsp");
        return null;
    }

    /**
     * 更新用户
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
        String username = request.getParameter("username");
        String oldPassWord = DigestUtils.md5Hex(request.getParameter("oldpassword"));
        String newPassWord = DigestUtils.md5Hex(request.getParameter("newpassword"));
        Administrator admin = (Administrator) request.getSession().getAttribute("admin");
        if (!(admin.getPassWord()).equals(oldPassWord)) {//原密码错误
            datas.put("result", "false");
            datas.put("msg", "原密码不正确！");
        } else {
            admin.setPassWord(newPassWord);
            admin.setName(username);
            adminService.update(admin);
            datas.put("result", "true");
            datas.put("msg", "修改成功！");
        }
        JsonUtil.writeJson(response, JsonUtil.mapToJson(datas));
        return null;
    }

}
