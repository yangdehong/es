package com.ydh.redsheep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
*
* @author : yangdehong
* @date : 2019-08-29 19:17
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class EsSearchPageBO extends EsSearchBaseBO implements Serializable {


    /**
     * 分页开始
     */
    private Integer from = 0;
    /**
     * 每页条数
     */
    private Integer size = 100;



}
