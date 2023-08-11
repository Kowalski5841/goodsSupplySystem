package com.kowalski.service.impl;

import com.kowalski.dao.IGoodsDao;
import com.kowalski.dao.impl.GoodsDaoImpl;
import com.kowalski.pojo.Goods;
import com.kowalski.utils.LayUITableResult;
import com.kowalski.pojo.query.GoodsQuery;
import com.kowalski.service.IGoodsService;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 20:47
 */
public class GoodsServiceImpl implements IGoodsService {
    private IGoodsDao iGoodsDao = new GoodsDaoImpl();
    @Override
    public LayUITableResult selectByPage(GoodsQuery gq) {
        List<Goods> list = iGoodsDao.selectByPage(gq);
        Integer count = iGoodsDao.selectCount(gq);
        return LayUITableResult.ok(count, list);
    }

    @Override
    public boolean add(Goods goods) {
        int count = iGoodsDao.add(goods);
        return count == 1;
    }

    @Override
    public boolean delete(int id) {
        int count = iGoodsDao.delete(id);
        return count == 1;
    }

    @Override
    public boolean deleteAll(String[] ids) {
        int count = 0;
        for (String id : ids) {
            count += iGoodsDao.delete(Integer.parseInt(id));
        }
        return  count == ids.length;
    }

    @Override
    public Goods getGoodsUpdatePage(int id) {
        System.out.println("GoodsServiceImpl.getGoodsUpdatePage");
        return iGoodsDao.getGoodsUpdatePage(id);
    }

    @Override
    public boolean update(Goods goods) {
        System.out.println("GoodsServiceImpl.update");
        int count = iGoodsDao.update(goods);
        return count == 1;
    }
}
