package com.kowalski.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 19:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    private Integer id;
    private String name;
    private String address;

    public Shop(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Shop(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
