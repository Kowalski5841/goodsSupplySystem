package com.kowalski.dao;

import com.kowalski.pojo.SupplyRecords;
import com.kowalski.pojo.query.SupplyRecordsQuery;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/12 11:07
 */
public interface ISupplyRecordsDao {
    List<SupplyRecords> select(SupplyRecordsQuery supplyRecordsQuery);

    Integer selectCount(SupplyRecordsQuery supplyRecordsQuery);
}
