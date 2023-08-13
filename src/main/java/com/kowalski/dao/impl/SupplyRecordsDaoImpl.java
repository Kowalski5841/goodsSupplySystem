package com.kowalski.dao.impl;

import com.kowalski.dao.ISupplyRecordsDao;
import com.kowalski.pojo.Supply;
import com.kowalski.pojo.SupplyRecords;
import com.kowalski.pojo.query.SupplyRecordsQuery;
import com.kowalski.pojo.vo.SaleRecordsVO;
import com.kowalski.pojo.vo.SupplyRecordsVO;
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
public class SupplyRecordsDaoImpl implements ISupplyRecordsDao {
    @Override
    @SneakyThrows
    public List<SupplyRecords> select(SupplyRecordsQuery supplyRecordsQuery) {
        System.out.println("SupplyRecordsDaoImpl.select");
        List<SupplyRecords> list = new ArrayList<>();
        int offset =(supplyRecordsQuery.getPage() - 1) *supplyRecordsQuery.getLimit();
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT sr.id,g.`name` as 'goods_name',g.specs,g.price,sr.count,shop.`name` as 'shop_name',shop.address as 'shop_address', s.`name` as 'supply_name', s.address as 'supply_address'\n" +
                "FROM supply_records as sr\n" +
                "INNER JOIN goods as g\n" +
                "on goods_id = g.id\n" +
                "inner JOIN shop \n" +
                "on shop_id = shop.id\n" +
                "INNER JOIN supply as s\n" +
                "ON supply_id = s.id\n" +
                "WHERE 1=1 ";
        List<Object> queryList = new ArrayList<>();

        String goodsName = supplyRecordsQuery.getGoodsName();
        if(goodsName != null && !"".equals(goodsName)){
            sql += " and g.`name` like ? ";
            queryList.add("%" + goodsName + "%");
        }

        String shopName = supplyRecordsQuery.getShopName();
        if(shopName != null && !"".equals(shopName)){
            sql += " and shop.`name` like ? ";
            queryList.add("%" + shopName + "%");
        }

        String supplyName = supplyRecordsQuery.getSupplyName();
        if(supplyName != null && !"".equals(supplyName)){
            sql += " and s.`name` like ? ";
            queryList.add("%" + supplyName + "%");
        }
        sql += " limit ?,? ";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        for (int i = 0; i < queryList.size(); i++) {
            pStmt.setObject(i + 1, queryList.get(i));
        }
        pStmt.setInt(queryList.size() + 1, offset);
        pStmt.setInt(queryList.size() + 2, supplyRecordsQuery.getLimit());


        System.out.println("执行的sql是" + pStmt);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String goods_name = rs.getString("goods_name");
            String specs = rs.getString("specs");
            double price = rs.getDouble("price");
            int count = rs.getInt("count");
            String shop_name = rs.getString("shop_name");
            String shop_address = rs.getString("shop_address");
            String supply_name = rs.getString("supply_name");
            String supply_address = rs.getString("supply_address");
            SupplyRecords supplyRecords = new SupplyRecords(id, goods_name, specs, price, count, shop_name,
                                                            shop_address, supply_name, supply_address);
            list.add(supplyRecords);
        }
        JDBCUtil.close(conn, pStmt, rs);
        return list;
    }

    @Override
    @SneakyThrows
    public Integer selectCount(SupplyRecordsQuery supplyRecordsQuery) {
        Integer count = 0;
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT count(*)\n" +
                "FROM supply_records as sr\n" +
                "INNER JOIN goods as g\n" +
                "on goods_id = g.id\n" +
                "inner JOIN shop \n" +
                "on shop_id = shop.id\n" +
                "INNER JOIN supply as s\n" +
                "ON supply_id = s.id\n" +
                "WHERE 1=1 ";
        List<Object> queryList = new ArrayList<>();
        String goodsName = supplyRecordsQuery.getGoodsName();
        if(goodsName != null && !"".equals(goodsName)){
            sql += " and g.`name` like ? ";
            queryList.add("%" + goodsName + "%");
        }

        String shopName = supplyRecordsQuery.getShopName();
        if(shopName != null && !"".equals(shopName)){
            sql += " and shop.`name` like ? ";
            queryList.add("%" + shopName + "%");
        }

        String supplyName = supplyRecordsQuery.getSupplyName();
        if(supplyName != null && !"".equals(supplyName)){
            sql += " and s.`name` like ? ";
            queryList.add("%" + supplyName + "%");
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
    public List<SupplyRecordsVO> selectCount() {
        System.out.println("ClassDaoImpl.selectClassCount");
        Connection conn = JDBCUtil.getConnection();
        List<SupplyRecordsVO> list = new ArrayList<>();
        String sql = "SELECT  goods.`name`, SUM(count) as s\n" +
                "FROM supply_records\n" +
                "INNER JOIN supply \n" +
                "ON supply.id = supply_id\n" +
                "INNER JOIN goods\n" +
                "ON goods.id = goods_id\n" +
                "WHERE 1=1\n" +
                "GROUP BY goods.`name`";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        while(rs.next()){
            String goods = rs.getString("name");
            int value = rs.getInt("s");
            SupplyRecordsVO srv = new SupplyRecordsVO(goods, value);
            list.add(srv);
        }
        return list;
    }

    @Override
    @SneakyThrows
    public int add(int supplyId, int shopId, int goodsId, int count) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "insert into supply_records(supply_id, shop_id, goods_id, count) values(?,?,?,?)";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, supplyId);
        pStmt.setInt(2, shopId);
        pStmt.setInt(3, goodsId);
        pStmt.setInt(4, count);
        System.out.println("本次执行的语句时是" + pStmt);
        int i = pStmt.executeUpdate();
        JDBCUtil.close(conn, pStmt, null);
        return i;
    }
}
