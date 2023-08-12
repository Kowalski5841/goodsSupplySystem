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
   private int id;
   private String goodsName;
   private String specs;
   private double price;
   private int count;
   private String shopName;
   private String shopAddress;
   private String supplyName;
   private String supplyAddress;
}
