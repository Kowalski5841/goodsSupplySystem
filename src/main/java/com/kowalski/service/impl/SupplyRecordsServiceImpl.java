package com.kowalski.service.impl;

import com.kowalski.dao.ISupplyRecordsDao;
import com.kowalski.dao.impl.SupplyRecordsDaoImpl;
import com.kowalski.pojo.SupplyRecords;
import com.kowalski.pojo.query.SupplyRecordsQuery;
import com.kowalski.pojo.vo.SupplyRecordsVO;
import com.kowalski.service.ISupplyRecordsService;
import com.kowalski.utils.LayUITableResult;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/12 10:42
 */
public class SupplyRecordsServiceImpl implements ISupplyRecordsService {
    private ISupplyRecordsDao iSupplyRecordsDao = new SupplyRecordsDaoImpl();
    @Override
    public LayUITableResult select(SupplyRecordsQuery supplyRecordsQuery) {
        System.out.println("SupplyRecordsServiceImpl.select");
        List<SupplyRecords> list = iSupplyRecordsDao.select(supplyRecordsQuery);
        Integer total = iSupplyRecordsDao.selectCount(supplyRecordsQuery);
        return LayUITableResult.ok(total, list);
    }

    @Override
    public boolean add(int supplyId, int shopId, int goodsId, int count) {
        System.out.println("SupplyRecordsServiceImpl.add");
        int i = iSupplyRecordsDao.add(supplyId, shopId, goodsId, count);
        return i == 1;
    }

    @Override
    public List<SupplyRecordsVO> selectCount() {
        System.out.println("SupplyRecordsServiceImpl.selectCount");
        return iSupplyRecordsDao.selectCount();
    }
}
