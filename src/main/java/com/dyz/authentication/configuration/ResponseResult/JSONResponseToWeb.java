package com.dyz.authentication.configuration.ResponseResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName JSONResponseToWeb
 * @Author Duan yuzhe
 * @Date 2020/8/30
 * @description 封装输出JSON格式的类
 */
public class JSONResponseToWeb {

    /*
     * @Description 输出JSON给Web
     * @param request
	 * @param response
	 * @param data
     * @return void
     **/
    public void WriteJSON(HttpServletRequest request,
                          HttpServletResponse response,
                          Object data) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        //输出JSON
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(data));
        out.flush();
        out.close();
    }
}
