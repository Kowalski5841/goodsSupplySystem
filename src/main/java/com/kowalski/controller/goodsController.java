package com.kowalski.controller;

import com.kowalski.pojo.Goods;
import com.kowalski.pojo.query.GoodsQuery;
import com.kowalski.service.IGoodsService;
import com.kowalski.service.impl.GoodsServiceImpl;
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
        System.out.println("goodsController.service");
        String method = req.getParameter("method");
        if(method == null || "".equals(method)){
            method = "selectByPage";
        }
        switch (method){
            case "selectByPage":
                selectByPage(req, resp);
                break;
            case "add":
                add(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            case "deleteAll":
                deleteAll(req, resp);
                break;
            case "getGoodsUpdatePage":
                getGoodsUpdatePage(req, resp);
                break;
            case "updateGoods":
                update(req, resp);
                break;
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("goodsController.update");
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String specs = req.getParameter("specs");
        double price = Double.parseDouble(req.getParameter("price"));
        int store = Integer.parseInt(req.getParameter("store"));
        Goods goods = new Goods(id, name, specs, price, store);
        boolean isSuccess = iGoodsService.update(goods);
        Result result = isSuccess ? Result.ok("编辑成功") : Result.error("编辑失败");
        JSONUtil.toJSON(resp, result);
    }

    @SneakyThrows
    private void getGoodsUpdatePage(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("goodsController.getGoodsUpdatePage");
        int id = Integer.parseInt(req.getParameter("id"));
        Goods goods = iGoodsService.getGoodsUpdatePage(id);
        req.setAttribute("goods", goods);
        req.getRequestDispatcher("update.jsp").forward(req, resp);
    }

    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("goodsController.deleteAll");
        String[] ids = req.getParameterValues("ids[]");
        boolean isSuccess = iGoodsService.deleteAll(ids);
        Result result = isSuccess ? Result.ok("删除成功") : Result.error("删除失败");
        JSONUtil.toJSON(resp, result);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("goodsController.delete");
        int id = Integer.parseInt(req.getParameter("id"));
        boolean isSuccess = iGoodsService.delete(id);
        Result result = isSuccess ? Result.ok("删除成功") : Result.error("删除失败");
        JSONUtil.toJSON(resp, result);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("goodsController.add");
        String name = req.getParameter("name");
        String specs = req.getParameter("specs");
        String price = req.getParameter("price");
        String store = req.getParameter("store");
        Goods goods = new Goods(name, specs, Double.parseDouble(price), Integer.parseInt(store));
        boolean isSuccess = iGoodsService.add(goods);
        Result result = isSuccess ? Result.ok("添加成功") : Result.error("添加失败");
        JSONUtil.toJSON(resp, result);
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
