package com.kowalski.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kowalski.pojo.Goods;
import com.kowalski.pojo.Shop;
import com.kowalski.pojo.Supply;
import com.kowalski.pojo.query.SaleRecordsQuery;
import com.kowalski.service.ISaleRecordsService;
import com.kowalski.service.impl.SaleRecordsServiceImpl;
import com.kowalski.utils.JDBCUtil;
import com.kowalski.utils.JSONUtil;
import com.kowalski.utils.LayUITableResult;
import com.kowalski.utils.Result;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/12 9:53
 */
@WebServlet("/saleRecords")
public class SaleRecordsController extends HttpServlet {
    private ISaleRecordsService iSaleRecordsService = new SaleRecordsServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("SaleRecordsController.service");
        String method = req.getParameter("method");
        switch (method) {
            case "select":
                select(req, resp);
                break;
            case "selectGoods":
                selectGoods(req, resp);
                break;
            case "add":
                add(req, resp);
                break;
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        int goodsId = Integer.parseInt(req.getParameter("goods"));
        String customer = req.getParameter("customer");

        int sold = Integer.parseInt(req.getParameter("sold"));
        boolean isSuccess = iSaleRecordsService.add(goodsId, customer, sold);
        Result result = isSuccess ? Result.ok("添加成功") : Result.error("添加失败");
        JSONUtil.toJSON(resp, result);
    }

    @SneakyThrows
    private void selectGoods(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("SaleRecordsController.selectGoods");
        Connection conn = JDBCUtil.getConnection();
        String sql = "select id, name from goods ";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        System.out.println(pStmt);
        ResultSet rs = pStmt.executeQuery();
        List<Goods> list = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Goods goods = new Goods(id, name);
            list.add(goods);
        }
        System.out.println(list);
        resp.setContentType("text/html;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(), list);
    }

    private void select(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("SaleRecordsController.select");
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

        String goodsName = req.getParameter("goodsName");
        String customer = req.getParameter("customer");
        SaleRecordsQuery saleRecordsQuery = new SaleRecordsQuery(page, limit, goodsName, customer);
        LayUITableResult layUITableResult = iSaleRecordsService.select(saleRecordsQuery);
        JSONUtil.toJSON(resp, layUITableResult);
    }
}
