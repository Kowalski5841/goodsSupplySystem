package com.kowalski.pojo.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/12 9:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyRecordsQuery {
    private Integer page;
    private Integer limit;
    private String goodsName;
    private String shopName;
    private String supplyName;
}
