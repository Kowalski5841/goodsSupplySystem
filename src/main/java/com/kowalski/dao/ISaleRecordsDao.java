package com.kowalski.dao;

import com.kowalski.pojo.SaleRecords;
import com.kowalski.pojo.query.SaleRecordsQuery;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/12 11:07
 */
public interface ISaleRecordsDao {
    List<SaleRecords> select(SaleRecordsQuery saleRecordsQuery);

    Integer selectCount(SaleRecordsQuery saleRecordsQuery);

    int add(int goodsId, String customer, int sold);
}
