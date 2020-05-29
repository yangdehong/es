package com.ydh.redsheep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.elasticsearch.search.sort.SortOrder;

import java.io.Serializable;
import java.util.Map;

/**
*
* @author : yangdehong
* @date : 2019-08-29 19:17
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class EsSearchBaseBO extends EsBaseBO implements Serializable {

    /**
     * 使用LinkedHashMap，不然排序的顺序会乱
     */
    private Map<String, SortOrder> sortFiled;
    /**
     * 显示的字段
     */
    private String[] includeFields;
    /**
     * 不显示的字段，优先级高于includeFields
     */
    private String[] excludeFields;
    /**
     * 完全匹配
     */
    private ParamFieldBO termField;
    /**
     * 前缀匹配
     */
    private ParamFieldBO prefixField;
    /**
     * 模糊匹配，下面的4个参数都是用来匹配fuzzy的
     */
    private ParamFieldFuzzyBO fuzzyField;
    /**
     * 短语匹配
     */
    private ParamFieldBO phraseField;
    /**
     * 短语匹配（前缀）
     */
    private ParamFieldBO phrasePrefixField;
    /**
     * 短语匹配（前缀）
     */
    private ParamFieldBO matchField;
    /**
     * 范围匹配
     */
    private EsRangeBO esRangeBO;
    /**
     * 是否存在匹配
     */
    private String existsField;
    /**
     * 正则匹配
     */
    private ParamFieldBO regexpField;

    /**
     * 复合查询，可以将上面的其他条件都放到复合里面组合起来一起当做条件
     */
    // and，过滤，不参与打分，所以效率是最高的，其他和must一模一样
    private Map<String, Object> filterFiled;
    // and，如果有多个条件，这些条件都必须满足 and与
    private Map<String, Object> mustFiled;
    // and，和must相反，必须都不满足条件才可以匹配到
    private Map<String, Object> notMustFiled;
    // or，如果有多个条件，满足一个或多个即可
    private Map<String, Object> shouldFiled;

}
