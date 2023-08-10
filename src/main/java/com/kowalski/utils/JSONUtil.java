package com.kowalski.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/3 15:44
 */
public class JSONUtil {
    @SneakyThrows
    public static void toJSON(HttpServletResponse resp, Object obj){
        resp.setContentType("text/html;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(), obj);
    }
}
