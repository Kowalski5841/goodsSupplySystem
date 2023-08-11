package com.kowalski.service;

import com.kowalski.pojo.Supply;
import com.kowalski.pojo.query.SupplyQuery;
import com.kowalski.utils.LayUITableResult;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 20:47
 */
public interface ISupplyService {
    LayUITableResult selectByPage(SupplyQuery gq);

    boolean add(Supply supply);

    boolean delete(int id);

    boolean deleteAll(String[] ids);

    Supply getSupplyUpdatePage(int id);

    boolean update(Supply supply);
}
