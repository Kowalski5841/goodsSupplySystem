package com.kowalski.service.impl;

import com.kowalski.dao.ISaleRecordsDao;
import com.kowalski.dao.impl.SaleRecordsDaoImpl;
import com.kowalski.pojo.SaleRecords;
import com.kowalski.pojo.query.SaleRecordsQuery;
import com.kowalski.service.ISaleRecordsService;
import com.kowalski.utils.LayUITableResult;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/12 10:42
 */
public class SaleRecordsServiceImpl implements ISaleRecordsService {
    private ISaleRecordsDao iSaleRecordsDao = new SaleRecordsDaoImpl();
    @Override
    public LayUITableResult select(SaleRecordsQuery saleRecordsQuery) {
        System.out.println("SaleRecordsServiceImpl.select");
        List<SaleRecords> list = iSaleRecordsDao.select(saleRecordsQuery);
        Integer total = iSaleRecordsDao.selectCount(saleRecordsQuery);
        return LayUITableResult.ok(total, list);
    }

    @Override
    public boolean add(int goodsId, String customer, int sold) {
        System.out.println("SaleRecordsServiceImpl.add");
        int i = iSaleRecordsDao.add(goodsId, customer, sold);
        return i == 1;
    }
}
