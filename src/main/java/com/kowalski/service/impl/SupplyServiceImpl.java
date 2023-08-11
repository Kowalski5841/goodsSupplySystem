package com.kowalski.service.impl;

import com.kowalski.dao.ISupplyDao;
import com.kowalski.dao.impl.SupplyDaoImpl;
import com.kowalski.pojo.Supply;
import com.kowalski.pojo.query.SupplyQuery;
import com.kowalski.service.ISupplyService;
import com.kowalski.utils.LayUITableResult;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 20:47
 */
public class SupplyServiceImpl implements ISupplyService {
    private ISupplyDao iSupplyDao = new SupplyDaoImpl();
    @Override
    public LayUITableResult selectByPage(SupplyQuery gq) {
        List<Supply> list = iSupplyDao.selectByPage(gq);
        Integer count = iSupplyDao.selectCount(gq);
        return LayUITableResult.ok(count, list);
    }

    @Override
    public boolean add(Supply supply) {
        int count = iSupplyDao.add(supply);
        return count == 1;
    }

    @Override
    public boolean delete(int id) {
        int count = iSupplyDao.delete(id);
        return count == 1;
    }

    @Override
    public boolean deleteAll(String[] ids) {
        int count = 0;
        for (String id : ids) {
            count += iSupplyDao.delete(Integer.parseInt(id));
        }
        return  count == ids.length;
    }

    @Override
    public Supply getSupplyUpdatePage(int id) {
        System.out.println("SupplyServiceImpl.getSupplyUpdatePage");
        return iSupplyDao.getSupplyUpdatePage(id);
    }

    @Override
    public boolean update(Supply supply) {
        System.out.println("SupplyServiceImpl.update");
        int count = iSupplyDao.update(supply);
        return count == 1;
    }
}
