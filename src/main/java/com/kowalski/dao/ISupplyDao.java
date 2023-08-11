package com.kowalski.dao;

import com.kowalski.pojo.Supply;
import com.kowalski.pojo.query.SupplyQuery;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 21:10
 */
public interface ISupplyDao {
    List<Supply> selectByPage(SupplyQuery gq);

    Integer selectCount(SupplyQuery gq);

    int add(Supply supply);

    int delete(int id);

    Supply getSupplyUpdatePage(int id);

    int update(Supply supply);
}
