package com.kowalski.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/8/10 19:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supply {
    private Integer id;
    private String name;
    private String address;
}
