package com.kowalski.service.impl;

import com.kowalski.dao.ISupplyRecordsDao;
import com.kowalski.dao.impl.SupplyRecordsDaoImpl;
import com.kowalski.pojo.SupplyRecords;
import com.kowalski.pojo.query.SupplyRecordsQuery;
import com.kowalski.service.ISupplyRecordService;
import com.kowalski.utils.LayUITableResult;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/12 10:42
 */
public class SupplyRecordsServiceImpl implements ISupplyRecordService {
    private ISupplyRecordsDao iSupplyRecordsDao = new SupplyRecordsDaoImpl();
    @Override
    public LayUITableResult select(SupplyRecordsQuery supplyRecordsQuery) {
        System.out.println("SupplyRecordsServiceImpl.select");
        List<SupplyRecords> list = iSupplyRecordsDao.select(supplyRecordsQuery);
        Integer total = iSupplyRecordsDao.selectCount(supplyRecordsQuery);
        return LayUITableResult.ok(total, list);
    }
}
