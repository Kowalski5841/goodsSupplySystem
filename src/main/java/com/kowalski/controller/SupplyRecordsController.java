package com.kowalski.controller;

import com.kowalski.pojo.query.GoodsQuery;
import com.kowalski.pojo.query.SupplyRecordsQuery;
import com.kowalski.service.ISupplyRecordService;
import com.kowalski.service.impl.SupplyRecordsServiceImpl;
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
 * @Date 2023/8/12 9:53
 */
@WebServlet("/supplyRecords")
public class SupplyRecordsController extends HttpServlet {
    private ISupplyRecordService iSupplyRecordService = new SupplyRecordsServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("SupplyRecordsController.service");
        String method = req.getParameter("method");
        switch (method) {
            case "select":
                select(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("SupplyRecordsController.delete");
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
        LayUITableResult layUITableResult = iSupplyRecordService.select(supplyRecordsQuery);
        JSONUtil.toJSON(resp, layUITableResult);
    }
}
