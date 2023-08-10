package com.kowalski.service;

import com.kowalski.utils.LayUITableResult;
import com.kowalski.pojo.query.GoodsQuery;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 20:47
 */
public interface IGoodsService {
    LayUITableResult selectByPage(GoodsQuery gq);
}
