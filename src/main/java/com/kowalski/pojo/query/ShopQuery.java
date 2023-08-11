package com.kowalski.pojo.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 20:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopQuery {
    private Integer page;
    private Integer limit;
    private Integer id;
    private String name;
}
