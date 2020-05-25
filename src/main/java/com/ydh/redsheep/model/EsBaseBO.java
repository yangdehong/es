package com.ydh.redsheep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
* @author : yangdehong
* @date : 2019-09-04 14:20
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsBaseBO {

    private String index;

    private String type;

    private String id;

}
