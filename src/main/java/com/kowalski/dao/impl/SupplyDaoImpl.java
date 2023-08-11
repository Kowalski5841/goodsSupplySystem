package com.kowalski.dao.impl;

import com.kowalski.dao.ISupplyDao;
import com.kowalski.pojo.Supply;
import com.kowalski.pojo.query.SupplyQuery;
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
public class SupplyDaoImpl implements ISupplyDao {
    @SneakyThrows
    @Override
    public List<Supply> selectByPage(SupplyQuery gq) {
        List<Supply> list = new ArrayList<>();
        int offset =(gq.getPage() - 1) *gq.getLimit();
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT id, name, address FROM supply where 1=1 ";
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
            String address = rs.getString("address");
            Supply supply = new Supply(id, name, address);
            list.add(supply);
        }
        JDBCUtil.close(conn, pStmt, rs);
        return list;
    }

    @Override
    @SneakyThrows
    public Integer selectCount(SupplyQuery gq) {
        Integer count = 0;
        Connection conn = JDBCUtil.getConnection();
        String sql = "select count(*) from supply where 1=1 ";
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
    public int add(Supply supply) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "insert into supply(name, address) values(?,?)";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, supply.getName());
        pStmt.setString(2, supply.getAddress());
        System.out.println("本次执行的语句时是" + pStmt);
        int count = pStmt.executeUpdate();
        JDBCUtil.close(conn, pStmt, null);
        return count;
    }

    @Override
    @SneakyThrows
    public int delete(int id) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "delete from supply where id = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, id);
        System.out.println("本次执行的语句时是" + pStmt);
        int count = pStmt.executeUpdate();
        JDBCUtil.close(conn, pStmt, null);
        return count;
    }

    @SneakyThrows
    @Override
    public Supply getSupplyUpdatePage(int id) {
        Supply supply = null;
        Connection conn = JDBCUtil.getConnection();
        String sql = "select * from supply where id = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, id);
        System.out.println(pStmt);
        ResultSet rs = pStmt.executeQuery();
        if(rs.next()){
            String name = rs.getString("name");
            String address = rs.getString("address");
            supply = new Supply(id, name, address);
        }
        System.out.println("获取到的对象：" + supply);
        return supply;
    }

    @Override
    @SneakyThrows
    public int update(Supply supply) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "update supply set name = ?, address = ? where id = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, supply.getName());
        pStmt.setString(2, supply.getAddress());
        pStmt.setInt(3, supply.getId());
        System.out.println("本次执行的语句时是" + pStmt);
        int count = pStmt.executeUpdate();
        JDBCUtil.close(conn, pStmt, null);
        return count;
    }
}
