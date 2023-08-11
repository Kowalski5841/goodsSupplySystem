package com.kowalski.service;

import com.kowalski.pojo.Shop;
import com.kowalski.pojo.query.ShopQuery;
import com.kowalski.utils.LayUITableResult;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 20:47
 */
public interface IShopService {
    LayUITableResult selectByPage(ShopQuery gq);

    boolean add(Shop shop);

    boolean delete(int id);

    boolean deleteAll(String[] ids);

    Shop getShopUpdatePage(int id);

    boolean update(Shop shop);
}
