package com.kowalski.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Kowalski
 * @version 1.0
 * @Date 2023/7/28 9:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo<T> {
    private List<T> list;
    private Integer totalPage;
    private Integer pageNo;
    private Integer pageSize;
}
