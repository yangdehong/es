package com.ydh.redsheep.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * @author : yangdehong
 * @date : 2020-06-28 09:47
 */
@Data
@Document(indexName = "help_center_article")
public class HelpCenterArticlePO implements Serializable {

    private static final long serialVersionUID = 8801506287677861621L;

    @Id
    private String id;

    @Field(store = true, analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String name;

    @Field(store = true, analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String context;

    /**
     * 状态 0-未发布 1-发布  PublicEnum
     */
    @Field(store = true, type = FieldType.Text)
    private String status;

    /**
     * 菜单id
     */
    @Field(store = true, type = FieldType.Long)
    private Long helpCenterMenuId;
    /**
     * 点击量
     */
    @Field(store = true, type = FieldType.Long)
    private Long clickNumber;
    /**
     * 相关链接
     */

    @Field(index = false, store = true, type = FieldType.Nested)
    private List<HelpCenterArticleLinkedVO> relationLinks;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime updateTime;

    /**
     * 创建人的id
     */
    protected Long createUserId;
    /**
     * 更新人的id
     */
    protected Long updateUserId;

}
