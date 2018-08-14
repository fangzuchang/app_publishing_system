package com.publishsystem.action;

import com.publishsystem.util.BaseServlet;
import com.google.gson.Gson;
import com.publishsystem.po.Customer;
import com.publishsystem.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomePageAction extends BaseServlet {
    private static final Logger logger = LoggerFactory.getLogger(HomePageAction.class);
    CustomerService customerService = new CustomerService();

    /**
     * 首页
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String home(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Customer> customerList = customerService.queryAllCust();
        Map<String, Object> datas = new HashMap<String, Object>();
        //存数据
        datas.put("result", "true");
        datas.put("data", customerList);
        writeJson(response, datas);
        return null;
    }

    /**
     * 将map转换成json并发送给调用者
     *
     * @param response 响应
     * @param params   map对象
     * @throws IOException
     */
    private void writeJson(HttpServletResponse response,
                           Map<String, Object> params) throws IOException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(params);
        jsonString = jsonString.replace("\\", "").replaceAll("id\":", "id\":\"").replaceAll(",\"custName", "\",\"custName");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }

}
