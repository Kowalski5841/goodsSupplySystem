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

    @Override
    @SneakyThrows
    public int add(Goods goods) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "insert into goods(name, specs, price, store) values(?,?,?,?)";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, goods.getName());
        pStmt.setString(2, goods.getSpecs());
        pStmt.setDouble(3, goods.getPrice());
        pStmt.setInt(4, goods.getStore());
        System.out.println("本次执行的语句时是" + pStmt);
        int count = pStmt.executeUpdate();
        JDBCUtil.close(conn, pStmt, null);
        return count;
    }

    @Override
    @SneakyThrows
    public int delete(int id) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "delete from goods where id = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, id);
        System.out.println("本次执行的语句时是" + pStmt);
        int count = pStmt.executeUpdate();
        JDBCUtil.close(conn, pStmt, null);
        return count;
    }

    @SneakyThrows
    @Override
    public Goods getGoodsUpdatePage(int id) {
        Goods goods = null;
        Connection conn = JDBCUtil.getConnection();
        String sql = "select * from goods where id = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, id);
        System.out.println(pStmt);
        ResultSet rs = pStmt.executeQuery();
        if(rs.next()){
            String name = rs.getString("name");
            String specs = rs.getString("specs");
            double price = rs.getDouble("price");
            int store = rs.getInt("store");
            goods = new Goods(id, name, specs, price, store);
        }
        System.out.println("获取到的对象：" + goods);
        return goods;
    }

    @Override
    @SneakyThrows
    public int update(Goods goods) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "update goods set name = ?, specs = ?, price = ?, store = ? where id = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, goods.getName());
        pStmt.setString(2, goods.getSpecs());
        pStmt.setDouble(3, goods.getPrice());
        pStmt.setInt(4, goods.getStore());
        pStmt.setInt(5, goods.getId());
        System.out.println("本次执行的语句时是" + pStmt);
        int count = pStmt.executeUpdate();
        JDBCUtil.close(conn, pStmt, null);
        return count;
    }
}
