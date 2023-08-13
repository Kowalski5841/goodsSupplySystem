package com.kowalski.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/13 10:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleRecordsVO {
    private String goods;
    private Integer value;
}
