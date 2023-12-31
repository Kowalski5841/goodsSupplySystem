package com.kowalski.service;

import com.kowalski.pojo.query.SupplyRecordsQuery;
import com.kowalski.pojo.vo.SupplyRecordsVO;
import com.kowalski.utils.LayUITableResult;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/12 10:41
 */
public interface ISupplyRecordsService {

    LayUITableResult select(SupplyRecordsQuery supplyRecordsQuery);

    boolean add(int supplyId, int shopId, int goodsId, int count);

    List<SupplyRecordsVO> selectCount();
}
