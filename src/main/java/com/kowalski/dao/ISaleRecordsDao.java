package com.kowalski.dao;

import com.kowalski.pojo.SaleRecords;
import com.kowalski.pojo.query.SaleRecordsQuery;
import com.kowalski.pojo.vo.SaleRecordsVO;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/12 11:07
 */
public interface ISaleRecordsDao {
    List<SaleRecords> select(SaleRecordsQuery saleRecordsQuery);

    Integer selectCount(SaleRecordsQuery saleRecordsQuery);

    List<SaleRecordsVO> selectCount();

    int add(int goodsId, String customer, int sold);
}
