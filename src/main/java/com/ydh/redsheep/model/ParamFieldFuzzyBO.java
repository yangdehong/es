package com.ydh.redsheep.model;

import lombok.Data;
import org.elasticsearch.common.unit.Fuzziness;

/**
*
* @author : yangdehong
* @date : 2020-05-27 16:28
*/
@Data
public class ParamFieldFuzzyBO extends ParamFieldBO {
    /**
     * 模糊程度 Fuzziness.ONE 编辑一次  Fuzziness.TWO 编辑两次 Fuzziness.AUTO 2位以内必须完全匹配，3-5允许编辑一次，>5允许编辑两次
     */
    private Fuzziness fuzziness;
    /**
     * 前缀必须匹配的的长度
     */
    private Integer prefixLength;
    /**
     * 如果一个模糊查询扩展了三个或四个模糊选项， 这些新的模糊选项也许是有意义的。如 果它产生 1000 个模糊选项，那么就基本没有意义了。
     * 设置 max_expansions 用来限制将产生的模糊选项的总数量。模糊查询将收集匹配词项直到达到 max_expansions 的限制
     */
    private Integer maxExpansions;

}
