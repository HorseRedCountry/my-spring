package com.majortom.myspring.chapter2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 顾客 实体类
 * </p>
 *
 * @author Major Tom
 * @date 2023/6/1 23:07
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private long id;
    private String name;
    private String contact;
    private String telephone;
    private String email;
    private String remark;
}
