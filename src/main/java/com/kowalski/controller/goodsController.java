package com.kowalski.controller;

import com.kowalski.pojo.query.GoodsQuery;
import com.kowalski.service.IGoodsService;
import com.kowalski.service.impl.GoodsServiceImpl;
import com.kowalski.utils.JSONUtil;
import com.kowalski.utils.LayUITableResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 19:40
 */
@WebServlet("/goods")
public class goodsController extends HttpServlet {
    private IGoodsService iGoodsService = new GoodsServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("indexPage.service");
        String method = req.getParameter("method");
        if(method == null || "".equals(method)){
            method = "selectByPage";
        }
        switch (method){
            case "selectByPage":
                selectByPage(req, resp);
                break;
        }
    }

    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("goodsController.selectAll");
        //设置默认页数
        String pageStr = req.getParameter("page");
        if(pageStr == null || "".equals(pageStr)){
            pageStr = "1";
        }
        String limitStr = req.getParameter("limit");
        if(limitStr == null || "".equals(limitStr)){
            limitStr = "10";
        }
        int page = Integer.parseInt(pageStr);
        int limit = Integer.parseInt(limitStr);
        String id = req.getParameter("id");
        Integer i = null;
        if(id != null && !"".equals(id)){
            i = Integer.parseInt(id);
        }

        String name = req.getParameter("name");
        GoodsQuery gq = new GoodsQuery(page, limit, i, name);
        LayUITableResult layUITableResult = iGoodsService.selectByPage(gq);
        JSONUtil.toJSON(resp, layUITableResult);
    }
}
