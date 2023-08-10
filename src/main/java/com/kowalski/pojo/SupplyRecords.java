package com.kowalski.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 19:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyRecords {
   private Integer id;
   private Integer supplyId;
   private Integer shopId;
   private Integer goodsId;
   private Integer count;
}
