package com.kowalski.controller;

import com.kowalski.pojo.Shop;
import com.kowalski.pojo.query.ShopQuery;
import com.kowalski.service.IShopService;
import com.kowalski.service.impl.ShopServiceImpl;
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
@WebServlet("/shop")
public class ShopController extends HttpServlet {
    private IShopService iShopService = new ShopServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("shopController.service");
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
            case "getShopUpdatePage":
                getShopUpdatePage(req, resp);
                break;
            case "updateShop":
                update(req, resp);
                break;
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("shopController.update");
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        Shop shop = new Shop(id, name, address);
        boolean isSuccess = iShopService.update(shop);
        Result result = isSuccess ? Result.ok("编辑成功") : Result.error("编辑失败");
        JSONUtil.toJSON(resp, result);
    }

    @SneakyThrows
    private void getShopUpdatePage(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("shopController.getShopUpdatePage");
        int id = Integer.parseInt(req.getParameter("id"));
        Shop shop = iShopService.getShopUpdatePage(id);
        req.setAttribute("shop", shop);
        req.getRequestDispatcher("updateShop.jsp").forward(req, resp);
    }

    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("shopController.deleteAll");
        String[] ids = req.getParameterValues("ids[]");
        boolean isSuccess = iShopService.deleteAll(ids);
        Result result = isSuccess ? Result.ok("删除成功") : Result.error("删除失败");
        JSONUtil.toJSON(resp, result);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("shopController.delete");
        int id = Integer.parseInt(req.getParameter("id"));
        boolean isSuccess = iShopService.delete(id);
        Result result = isSuccess ? Result.ok("删除成功") : Result.error("删除失败");
        JSONUtil.toJSON(resp, result);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("shopController.add");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        Shop shop = new Shop(name, address);
        boolean isSuccess = iShopService.add(shop);
        Result result = isSuccess ? Result.ok("添加成功") : Result.error("添加失败");
        JSONUtil.toJSON(resp, result);
    }

    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("shopController.selectAll");
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
        ShopQuery gq = new ShopQuery(page, limit, i, name);
        LayUITableResult layUITableResult = iShopService.selectByPage(gq);
        JSONUtil.toJSON(resp, layUITableResult);
    }
}
