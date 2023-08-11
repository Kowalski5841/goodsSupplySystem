package com.kowalski.dao.impl;

import com.kowalski.dao.IShopDao;
import com.kowalski.pojo.Shop;
import com.kowalski.pojo.query.ShopQuery;
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
public class ShopDaoImpl implements IShopDao {
    @SneakyThrows
    @Override
    public List<Shop> selectByPage(ShopQuery shopQuery) {
        List<Shop> list = new ArrayList<>();
        int offset =(shopQuery.getPage() - 1) *shopQuery.getLimit();
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT id, name, address FROM shop where 1=1 ";
        List<Object> queryList = new ArrayList<>();

        Integer queryId = shopQuery.getId();
        if(queryId != null){
            sql += " and id = ? ";
            queryList.add(queryId);
        }

        String queryName = shopQuery.getName();
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
        pStmt.setInt(queryList.size() + 2, shopQuery.getLimit());


        System.out.println("执行的sql是" + pStmt);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String address = rs.getString("address");
            Shop shop = new Shop(id, name, address);
            list.add(shop);
        }
        JDBCUtil.close(conn, pStmt, rs);
        return list;
    }

    @Override
    @SneakyThrows
    public Integer selectCount(ShopQuery shopQuery) {
        Integer count = 0;
        Connection conn = JDBCUtil.getConnection();
        String sql = "select count(*) from shop where 1=1 ";
        List<Object> queryList = new ArrayList<>();
        Integer queryId = shopQuery.getId();
        if(queryId != null){
            sql += " and id = ? ";
            queryList.add(queryId);
        }

        String queryName = shopQuery.getName();
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
    public int add(Shop shop) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "insert into shop(name, address) values(?,?)";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, shop.getName());
        pStmt.setString(2, shop.getAddress());
        System.out.println("本次执行的语句时是" + pStmt);
        int count = pStmt.executeUpdate();
        JDBCUtil.close(conn, pStmt, null);
        return count;
    }

    @Override
    @SneakyThrows
    public int delete(int id) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "delete from shop where id = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, id);
        System.out.println("本次执行的语句时是" + pStmt);
        int count = pStmt.executeUpdate();
        JDBCUtil.close(conn, pStmt, null);
        return count;
    }

    @SneakyThrows
    @Override
    public Shop getShopUpdatePage(int id) {
        Shop shop = null;
        Connection conn = JDBCUtil.getConnection();
        String sql = "select * from shop where id = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, id);
        System.out.println(pStmt);
        ResultSet rs = pStmt.executeQuery();
        if(rs.next()){
            String name = rs.getString("name");
            String address = rs.getString("address");
            shop = new Shop(id, name, address);
        }
        System.out.println("获取到的对象：" + shop);
        return shop;
    }

    @Override
    @SneakyThrows
    public int update(Shop shop) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "update shop set name = ?, address = ? where id = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, shop.getName());
        pStmt.setString(2, shop.getAddress());
        pStmt.setInt(3, shop.getId());
        System.out.println("本次执行的语句时是" + pStmt);
        int count = pStmt.executeUpdate();
        JDBCUtil.close(conn, pStmt, null);
        return count;
    }
}
