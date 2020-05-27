package com.ydh.redsheep.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
*
* @author : yangdehong
* @date : 2020-05-25 16:46
*/
@Data
public class PersonPO implements Serializable {

    private String name;

    private String address;

    private String sex;

    private String email;

    private LocalDateTime birthDay;

    private Integer sort;

}
