package com.kowalski.dao;

import com.kowalski.pojo.Supply;
import com.kowalski.pojo.SupplyRecords;
import com.kowalski.pojo.query.SupplyRecordsQuery;
import com.kowalski.pojo.vo.SupplyRecordsVO;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/12 11:07
 */
public interface ISupplyRecordsDao {
    List<SupplyRecords> select(SupplyRecordsQuery supplyRecordsQuery);

    Integer selectCount(SupplyRecordsQuery supplyRecordsQuery);

    List<SupplyRecordsVO> selectCount();

    int add(int supplyId, int shopId, int goodsId, int count);
}
