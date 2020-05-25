package com.ydh.redsheep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
*
* @author : yangdehong
* @date : 2019-08-29 19:17
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsSearchScrollBO extends EsSearchBaseBO implements Serializable {


    /**
     * 每次滚动要获取的数据量
     */
    private Integer size = 10000;



}
