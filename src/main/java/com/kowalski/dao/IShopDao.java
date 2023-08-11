package com.kowalski.dao;

import com.kowalski.pojo.Shop;
import com.kowalski.pojo.query.ShopQuery;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 21:10
 */
public interface IShopDao {
    List<Shop> selectByPage(ShopQuery gq);

    Integer selectCount(ShopQuery gq);

    int add(Shop shop);

    int delete(int id);

    Shop getShopUpdatePage(int id);

    int update(Shop shop);
}
