package com.publishsystem.listen;

import com.publishsystem.po.Administrator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fzc
 * @date 2018-1-17 14:57
 */
public class LoginSessionListener implements HttpSessionAttributeListener {
    Log log = LogFactory.getLog(LoginSessionListener.class);
    //保存session
    Map<String, HttpSession> map = new HashMap<String, HttpSession>();


    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        //新建session属性名称
        String name = httpSessionBindingEvent.getName();
        if (name.equals("admin")) {//登录事件
            //增加的属性值
            Administrator admin = (Administrator) httpSessionBindingEvent.getValue();
            if (map.get(admin.getName()) != null) {//map中存在该账号
                HttpSession session = map.get(admin.getName());
                Administrator oldAdmin = (Administrator) session.getAttribute("admin");
                session.removeAttribute("admin");
                session.setAttribute("msg", "您的账号已经在其他机器上登录，您被迫下线。");
            }
            map.put(admin.getName(), httpSessionBindingEvent.getSession());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        String name = httpSessionBindingEvent.getName();
        if (name.equals("amdin")) {//注销
            //被移除的admin
            Administrator admin = (Administrator) httpSessionBindingEvent.getValue();
            map.remove(admin.getName());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        String name = httpSessionBindingEvent.getName();
        if (name.equals("admin")) {//没有注销的情况下，用另一个账号登录
            Administrator oldAdmin = (Administrator) httpSessionBindingEvent.getValue();
            //移除旧的登陆信息
            map.remove(oldAdmin.getName());
            //新的登录信息
            Administrator admin = (Administrator) httpSessionBindingEvent.getSession().getAttribute("admin");
            if (map.get(admin.getName()) != null) {
                HttpSession session = map.get(admin.getName());
                session.removeAttribute("admin");
                session.setAttribute("msg", "您的账号已经在其他机器上登录，您被迫下线。");
            }
            map.put(admin.getName(), httpSessionBindingEvent.getSession());
        }
    }
}
