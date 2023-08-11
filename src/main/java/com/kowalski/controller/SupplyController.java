package com.kowalski.controller;

import com.kowalski.pojo.Supply;
import com.kowalski.pojo.query.SupplyQuery;
import com.kowalski.service.ISupplyService;
import com.kowalski.service.impl.SupplyServiceImpl;
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
@WebServlet("/supply")
public class SupplyController extends HttpServlet {
    private ISupplyService iSupplyService = new SupplyServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("supplyController.service");
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
            case "getSupplyUpdatePage":
                getSupplyUpdatePage(req, resp);
                break;
            case "updateSupply":
                update(req, resp);
                break;
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("supplyController.update");
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        Supply supply = new Supply(id, name, address);
        boolean isSuccess = iSupplyService.update(supply);
        Result result = isSuccess ? Result.ok("编辑成功") : Result.error("编辑失败");
        JSONUtil.toJSON(resp, result);
    }

    @SneakyThrows
    private void getSupplyUpdatePage(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("supplyController.getSupplyUpdatePage");
        int id = Integer.parseInt(req.getParameter("id"));
        Supply supply = iSupplyService.getSupplyUpdatePage(id);
        req.setAttribute("supply", supply);
        req.getRequestDispatcher("updateSupply.jsp").forward(req, resp);
    }

    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("supplyController.deleteAll");
        String[] ids = req.getParameterValues("ids[]");
        boolean isSuccess = iSupplyService.deleteAll(ids);
        Result result = isSuccess ? Result.ok("删除成功") : Result.error("删除失败");
        JSONUtil.toJSON(resp, result);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("supplyController.delete");
        int id = Integer.parseInt(req.getParameter("id"));
        boolean isSuccess = iSupplyService.delete(id);
        Result result = isSuccess ? Result.ok("删除成功") : Result.error("删除失败");
        JSONUtil.toJSON(resp, result);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("supplyController.add");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        Supply supply = new Supply(name, address);
        boolean isSuccess = iSupplyService.add(supply);
        Result result = isSuccess ? Result.ok("添加成功") : Result.error("添加失败");
        JSONUtil.toJSON(resp, result);
    }

    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("supplyController.selectAll");
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
        SupplyQuery gq = new SupplyQuery(page, limit, i, name);
        LayUITableResult layUITableResult = iSupplyService.selectByPage(gq);
        JSONUtil.toJSON(resp, layUITableResult);
    }
}
