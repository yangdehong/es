package com.ydh.redsheep.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
* 帮助中心菜单
* @author : yangdehong
* @date : 2020-06-28 09:47
*/
@Data
public class HelpCenterArticleLinkedVO implements Serializable {

    private static final long serialVersionUID = 8600039942522767495L;

    private String id;

    private String name;

    /**
     * 状态 0-未发布 1-发布
     */
    private String status;

}
