package com.kowalski.dao.impl;

import com.kowalski.dao.ISaleRecordsDao;
import com.kowalski.pojo.SaleRecords;
import com.kowalski.pojo.query.SaleRecordsQuery;
import com.kowalski.pojo.vo.SaleRecordsVO;
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
 * @Date 2023/8/12 11:08
 */
public class SaleRecordsDaoImpl implements ISaleRecordsDao {
    @Override
    @SneakyThrows
    public List<SaleRecords> select(SaleRecordsQuery saleRecordsQuery) {
        System.out.println("SaleRecordsDaoImpl.select");
        List<SaleRecords> list = new ArrayList<>();
        int offset =(saleRecordsQuery.getPage() - 1) *saleRecordsQuery.getLimit();
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT sr.id, g.`name` as 'goods_name', customer, sold\n" +
                "FROM sale_records AS sr\n" +
                "INNER JOIN goods AS g\n" +
                "ON g.id = sr.goods_id\n" +
                "WHERE 1=1 ";
        List<Object> queryList = new ArrayList<>();

        String goodsName = saleRecordsQuery.getGoodsName();
        if(goodsName != null && !"".equals(goodsName)){
            sql += " and g.`name` like ? ";
            queryList.add("%" + goodsName + "%");
        }

        String queryCustomer = saleRecordsQuery.getCustomer();
        if(queryCustomer != null && !"".equals(queryCustomer)){
            sql += " AND customer LIKE ? ";
            queryList.add("%" + queryCustomer + "%");
        }

        sql += " limit ?,? ";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        for (int i = 0; i < queryList.size(); i++) {
            pStmt.setObject(i + 1, queryList.get(i));
        }
        pStmt.setInt(queryList.size() + 1, offset);
        pStmt.setInt(queryList.size() + 2, saleRecordsQuery.getLimit());


        System.out.println("执行的sql是" + pStmt);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String goods_name = rs.getString("goods_name");
            String customer = rs.getString("customer");
            int sold = rs.getInt("sold");

            SaleRecords saleRecords = new SaleRecords(id, goods_name, customer, sold);
            list.add(saleRecords);
        }
        JDBCUtil.close(conn, pStmt, rs);
        return list;
    }

    @Override
    @SneakyThrows
    public Integer selectCount(SaleRecordsQuery saleRecordsQuery) {
        Integer count = 0;
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT count(*)\n" +
                "FROM sale_records AS sr\n" +
                "INNER JOIN goods AS g\n" +
                "ON g.id = sr.goods_id\n" +
                "WHERE 1=1 ";
        List<Object> queryList = new ArrayList<>();
        String goodsName = saleRecordsQuery.getGoodsName();
        if(goodsName != null && !"".equals(goodsName)){
            sql += " and g.`name` like ? ";
            queryList.add("%" + goodsName + "%");
        }

        String queryCustomer = saleRecordsQuery.getCustomer();
        if(queryCustomer != null && !"".equals(queryCustomer)){
            sql += " AND customer LIKE ? ";
            queryList.add("%" + queryCustomer + "%");
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
    public List<SaleRecordsVO> selectCount() {
        System.out.println("ClassDaoImpl.selectClassCount");
        Connection conn = JDBCUtil.getConnection();
        List<SaleRecordsVO> list = new ArrayList<>();
        String sql = "SELECT g.`name`,sum(sold) as s\n" +
                "FROM sale_records AS sr\n" +
                "INNER JOIN goods AS g\n" +
                "ON g.id = sr.goods_id\n" +
                "WHERE 1=1\n" +
                "GROUP BY g.id;";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        while(rs.next()){
            String goods = rs.getString("name");
            int value = rs.getInt("s");
            SaleRecordsVO srv = new SaleRecordsVO(goods, value);
            list.add(srv);
        }
        return list;
    }

    @Override
    @SneakyThrows
    public int add(int goodsId, String customer, int sold) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "insert into sale_records(goods_id, customer, sold) values(?,?,?)";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, goodsId);
        pStmt.setString(2, customer);
        pStmt.setInt(3, sold);
        System.out.println("本次执行的语句时是" + pStmt);
        int i = pStmt.executeUpdate();
        JDBCUtil.close(conn, pStmt, null);
        return i;
    }
}
