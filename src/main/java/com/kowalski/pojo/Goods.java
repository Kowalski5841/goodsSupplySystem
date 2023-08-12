package com.kowalski.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 19:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private Integer id;
    private String name;
    private String specs;
    private Double price;
    private Integer store;

    public Goods(String name, String specs, Double price, Integer store) {
        this.name = name;
        this.specs = specs;
        this.price = price;
        this.store = store;
    }

    public Goods(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
