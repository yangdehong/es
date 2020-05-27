package com.ydh.redsheep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 范围查询
* @author : yangdehong
* @date : 2019-09-05 17:18
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsRangeBO<T> {

    private String filedName;

    private T minValue;

    private T maxValue;

}
