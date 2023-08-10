package com.kowalski.dao;

import com.kowalski.pojo.Goods;
import com.kowalski.pojo.query.GoodsQuery;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 21:10
 */
public interface IGoodsDao {
    List<Goods> selectByPage(GoodsQuery gq);

    Integer selectCount(GoodsQuery gq);
}
