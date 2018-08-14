package com.publishsystem.action;

import com.publishsystem.util.GraphicHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author fzc
 * @date 2018-7-6 18:15
 */
public class CaptchaAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获得 当前请求 对应的 会话对象
        HttpSession session = request.getSession();
        final int width = 180; // 图片宽度
        final int height = 40; // 图片高度
        final String imgType = "jpeg"; // 指定图片格式 (不是指MIME类型)
        final OutputStream output = response.getOutputStream(); // 获得可以向客户端返回图片的输出流
        // (字节流)
        // 创建验证码图片并返回图片上的字符串
        String code = GraphicHelper.create(width, height, imgType, output);
        // 建立 uri 和 相应的 验证码 的关联 ( 存储到当前会话对象的属性中 )
        session.setAttribute("code", code);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
