package com.publishsystem.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonUtil {
    /**
     * 将map转换成json并发送给调用者
     *
     * @param response 响应
     * @throws IOException
     */
    public static void writeJson(HttpServletResponse response,
                                 String jsonString) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }

    /**
     * map转json
     *
     * @param params
     * @return
     */
    public static String mapToJson(Map<String, Object> params) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return gson.toJson(params);
    }
}
