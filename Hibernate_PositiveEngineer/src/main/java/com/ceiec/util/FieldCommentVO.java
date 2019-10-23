package com.ceiec.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/10/21 0021
 * creat_time: 16:35
 **/
@Data
@AllArgsConstructor
public class FieldCommentVO {
    private String fieldName;

    private String comment;
}
