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
public class EsMultiSearchBO extends EsSearchBaseBO implements Serializable {

    /**
     * 分页开始
     */
    private Integer from = 0;
    /**
     * 每次滚动要获取的数据量
     */
    private Integer size = 20;


}
