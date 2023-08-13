package com.kowalski.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kowalski.pojo.vo.SaleRecordsVO;
import com.kowalski.pojo.vo.SupplyRecordsVO;
import com.kowalski.service.ISaleRecordsService;
import com.kowalski.service.ISupplyRecordsService;
import com.kowalski.service.impl.SaleRecordsServiceImpl;
import com.kowalski.service.impl.SupplyRecordsServiceImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/3 9:40
 */
@WebServlet("/echarts")
public class EChartsServlet extends HttpServlet {
    private ISaleRecordsService iSaleRecordsService = new SaleRecordsServiceImpl();
    private ISupplyRecordsService iSupplyRecordsService = new SupplyRecordsServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EChartsServlet.service");
        resp.setContentType("text/html;charset=utf-8");

        String method = req.getParameter("method");
        switch (method){
            case "sale":
                sale(req,resp);
                break;
            case "supply":
                supply(req, resp);
                break;
        }
    }

    @SneakyThrows
    private void supply(HttpServletRequest req, HttpServletResponse resp) {
        List<SupplyRecordsVO> list = iSupplyRecordsService.selectCount();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(), list);
    }

    @SneakyThrows
    private void sale(HttpServletRequest req, HttpServletResponse resp) {
        List<SaleRecordsVO> list = iSaleRecordsService.selectCount();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(), list);
    }
}
