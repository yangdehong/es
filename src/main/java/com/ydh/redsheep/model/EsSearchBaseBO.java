package com.ydh.redsheep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.elasticsearch.common.unit.Fuzziness;
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
    private Map<String, Object> fullMatchField;
    /**
     *
     */
    private EsParamsRangeBO esParamsRangeBO;
    /**
     * 模糊匹配
     */
    private Map<String, String> fuzzyField;

    /**
     * 模糊程度设置成1位
     */
    private Fuzziness fuzziness = Fuzziness.ONE;

    /**
     * 不能被 “模糊化” 的初始字符数
     */
    private Integer prefixLength = 0;

    /**
     * 如果一个模糊查询扩展了三个或四个模糊选项， 这些新的模糊选项也许是有意义的。如 果它产生 1000 个模糊选项，那么就基本没有意义了。
     * 设置 max_expansions 用来限制将产生的模糊选项的总数量。模糊查询将收集匹配词项直到达到 max_expansions 的限制
     */
    private Integer maxExpansions = 50;

    /**
     * bool的条件值，现在只用filter
     */
    private Map<String, Object> filterFiled;
    private Map<String, Object> mustFiled;
    private Map<String, Object> notMustFiled;
    private Map<String, Object> shouldFiled;


}
