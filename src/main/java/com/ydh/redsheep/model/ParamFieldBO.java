package com.ydh.redsheep.model;

import lombok.Data;

import java.io.Serializable;

/**
*
* @author : yangdehong
* @date : 2020-05-27 13:54
*/
@Data
public class ParamFieldBO<T> implements Serializable {

    private String fieldName;

    private T value;

}
