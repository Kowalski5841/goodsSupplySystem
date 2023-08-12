package com.kowalski.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kowalski.pojo.Goods;
import com.kowalski.pojo.Shop;
import com.kowalski.pojo.Supply;
import com.kowalski.pojo.query.SupplyRecordsQuery;
import com.kowalski.service.ISupplyRecordsService;
import com.kowalski.service.impl.SupplyRecordsServiceImpl;
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
@WebServlet("/supplyRecords")
public class SupplyRecordsController extends HttpServlet {
    private ISupplyRecordsService iSupplyRecordsService = new SupplyRecordsServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("SupplyRecordsController.service");
        String method = req.getParameter("method");
        switch (method) {
            case "select":
                select(req, resp);
                break;
            case "selectGoods":
                selectGoods(req, resp);
                break;
            case "selectSupply":
                selectSupply(req, resp);
                break;
            case "selectShop":
                selectShop(req, resp);
                break;
            case "add":
                add(req, resp);
                break;
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("-----------------");
        System.out.println(req.getParameter("shop"));
        System.out.println(req.getParameter("supply"));
        System.out.println(req.getParameter("goods"));
        System.out.println("-----------------");
        int supplyId = Integer.parseInt(req.getParameter("supply"));
        int shopId = Integer.parseInt(req.getParameter("shop"));
        int goodsId = Integer.parseInt(req.getParameter("goods"));
        int count = Integer.parseInt(req.getParameter("cc"));
        boolean isSuccess = iSupplyRecordsService.add(supplyId, shopId, goodsId, count);
        Result result = isSuccess ? Result.ok("添加成功") : Result.error("添加失败");
        JSONUtil.toJSON(resp, result);
    }

    @SneakyThrows
    private void selectShop(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("SupplyRecordsController.selectShop");
        Connection conn = JDBCUtil.getConnection();
        String sql = "select id, name from shop ";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        System.out.println(pStmt);
        ResultSet rs = pStmt.executeQuery();
        List<Shop> list = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Shop shop = new Shop(id, name);
            list.add(shop);
        }
        System.out.println(list);
        resp.setContentType("text/html;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(), list);
    }

    @SneakyThrows
    private void selectSupply(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("SupplyRecordsController.selectSupply");
        Connection conn = JDBCUtil.getConnection();
        String sql = "select id, name from supply ";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        System.out.println(pStmt);
        ResultSet rs = pStmt.executeQuery();
        List<Supply> list = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Supply supply = new Supply(id, name);
            list.add(supply);
        }
        System.out.println(list);
        resp.setContentType("text/html;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(), list);
    }

    @SneakyThrows
    private void selectGoods(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("SupplyRecordsController.selectGoods");
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
        System.out.println("SupplyRecordsController.select");
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
        String shopName = req.getParameter("shopName");
        String supplyName = req.getParameter("supplyName");
        SupplyRecordsQuery supplyRecordsQuery = new SupplyRecordsQuery(page, limit, goodsName, shopName, supplyName);
        System.out.println(supplyRecordsQuery);
        LayUITableResult layUITableResult = iSupplyRecordsService.select(supplyRecordsQuery);
        JSONUtil.toJSON(resp, layUITableResult);
    }
}
