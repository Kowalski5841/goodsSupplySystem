package com.kowalski.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 19:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleRecords {
    private Integer id;
    private Integer goods_id;
    private String customer;
    private Integer sold;
}
