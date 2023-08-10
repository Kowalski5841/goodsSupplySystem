package com.kowalski.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 20:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyForm {
    private Integer id;
    private Integer supplyID;
    private Integer goodsId;
}
