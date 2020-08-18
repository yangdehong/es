package com.ydh.redsheep.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
*
* @author : yangdehong
* @date : 2020-05-25 16:46
*/
@Data
@Document(indexName = "test_index")
public class PersonPO implements Serializable {

    @Id
    private String id;

    @Field(store = true, analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String name;

    @Field(store = true, analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String address;

    @Field(store = true, type = FieldType.Keyword)
    private String sex;

    @Field(store = true, type = FieldType.Text)
    private String email;

    @Field(store = true, type = FieldType.Date)
    private LocalDateTime birthDay;

    @Field(store = true, type = FieldType.Integer)
    private Integer sort;

//    @Parent(type = "text")
//    private String personId;

}
