package com.kowalski.dao.impl;

import com.kowalski.dao.IGoodsDao;
import com.kowalski.pojo.Goods;
import com.kowalski.pojo.query.GoodsQuery;
import com.kowalski.utils.JDBCUtil;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 21:11
 */
public class GoodsDaoImpl implements IGoodsDao {
    @SneakyThrows
    @Override
    public List<Goods> selectByPage(GoodsQuery gq) {
        List<Goods> list = new ArrayList<>();
        int offset =(gq.getPage() - 1) *gq.getLimit();
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT id, name, specs, price, store FROM goods where 1=1 ";
        List<Object> queryList = new ArrayList<>();

        Integer queryId = gq.getId();
        if(queryId != null){
            sql += " and id = ? ";
            queryList.add(queryId);
        }

        String queryName = gq.getName();
        if(queryName != null && !"".equals(queryName)){
            sql += " and name like ? ";
            queryList.add("%" + queryName + "%");
        }
        sql += " limit ?,? ";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        for (int i = 0; i < queryList.size(); i++) {
            pStmt.setObject(i + 1, queryList.get(i));
        }
        pStmt.setInt(queryList.size() + 1, offset);
        pStmt.setInt(queryList.size() + 2, gq.getLimit());


        System.out.println("执行的sql是" + pStmt);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String specs = rs.getString("specs");
            double price = rs.getDouble("price");
            int store = rs.getInt("store");
            Goods goods = new Goods(id, name, specs, price, store);
            list.add(goods);
        }
        JDBCUtil.close(conn, pStmt, rs);
        return list;
    }

    @Override
    @SneakyThrows
    public Integer selectCount(GoodsQuery gq) {
        Integer count = 0;
        Connection conn = JDBCUtil.getConnection();
        String sql = "select count(*) from goods where 1=1 ";
        List<Object> queryList = new ArrayList<>();
        Integer queryId = gq.getId();
        if(queryId != null){
            sql += " and id = ? ";
            queryList.add(queryId);
        }

        String queryName = gq.getName();
        if(queryName != null && !"".equals(queryName)){
            sql += " and name like ? ";
            queryList.add("%" + queryName + "%");
        }
        PreparedStatement pStmt = conn.prepareStatement(sql);
        for (int i = 0; i < queryList.size(); i++) {
            pStmt.setObject(i + 1, queryList.get(i));
        }
        System.out.println("执行的语句是" + pStmt);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next()){
            count =rs.getInt(1);
        }
        JDBCUtil.close(conn, pStmt, rs);
        return count;
    }
}
