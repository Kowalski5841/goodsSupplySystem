package com.kowalski.service.impl;

import com.kowalski.dao.IShopDao;
import com.kowalski.dao.impl.ShopDaoImpl;
import com.kowalski.pojo.Shop;
import com.kowalski.pojo.query.ShopQuery;
import com.kowalski.service.IShopService;
import com.kowalski.utils.LayUITableResult;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 20:47
 */
public class ShopServiceImpl implements IShopService {
    private IShopDao iShopDao = new ShopDaoImpl();
    @Override
    public LayUITableResult selectByPage(ShopQuery gq) {
        List<Shop> list = iShopDao.selectByPage(gq);
        Integer count = iShopDao.selectCount(gq);
        return LayUITableResult.ok(count, list);
    }

    @Override
    public boolean add(Shop shop) {
        int count = iShopDao.add(shop);
        return count == 1;
    }

    @Override
    public boolean delete(int id) {
        int count = iShopDao.delete(id);
        return count == 1;
    }

    @Override
    public boolean deleteAll(String[] ids) {
        int count = 0;
        for (String id : ids) {
            count += iShopDao.delete(Integer.parseInt(id));
        }
        return  count == ids.length;
    }

    @Override
    public Shop getShopUpdatePage(int id) {
        System.out.println("ShopServiceImpl.getShopUpdatePage");
        return iShopDao.getShopUpdatePage(id);
    }

    @Override
    public boolean update(Shop shop) {
        System.out.println("ShopServiceImpl.update");
        int count = iShopDao.update(shop);
        return count == 1;
    }
}
