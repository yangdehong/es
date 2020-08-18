package com.ydh.redsheep.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Parent;

/**
*
* @author : yangdehong
* @date : 2020-08-14 17:42
*/
@Data
@Document(indexName = "test_index")
public class ChildrenPO {

    @Id
    private Long id;

    @Field(store = true, analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String childrenName;

    @Parent(type = "test")
    private String personId;

}
