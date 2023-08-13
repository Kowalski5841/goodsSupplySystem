package com.kowalski.service;

import com.kowalski.pojo.query.SaleRecordsQuery;
import com.kowalski.pojo.vo.SaleRecordsVO;
import com.kowalski.utils.LayUITableResult;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/12 10:41
 */
public interface ISaleRecordsService {

    LayUITableResult select(SaleRecordsQuery saleRecordsQuery);


    boolean add(int goodsId, String customer, int sold);

    List<SaleRecordsVO> selectCount();
}
