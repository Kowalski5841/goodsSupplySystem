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
}
