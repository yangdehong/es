package com.ydh.redsheep.es.document_api;

import com.ydh.redsheep.mapper.HelpCenterArticleRepository;
import com.ydh.redsheep.mapper.PersonRepository;
import com.ydh.redsheep.model.entity.HelpCenterArticlePO;
import com.ydh.redsheep.model.entity.PersonPO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
*
* @author : yangdehong
* @date : 2019-08-28 17:25
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ElasticsearchDocument {

    @Resource
    private HelpCenterArticleRepository helpCenterArticleRepository;

    @Test
    public void test2() {
//        HelpCenterArticlePO helpCenterArticlePO = new HelpCenterArticlePO();
//        helpCenterArticlePO.setName("test1");
//        helpCenterArticlePO.setContext("大家好，我是中国人，我很厉害的，我有我的家");
//        helpCenterArticlePO.setHelpCenterMenuId(1L);
//        helpCenterArticlePO.setClickNumber(0L);
//        helpCenterArticlePO.setStatus("0");
//        helpCenterArticlePO.setRelationLinks(null);
//        helpCenterArticlePO.setCreateUserId(0L);
//        helpCenterArticlePO.setCreateTime(LocalDateTime.now());
//        helpCenterArticlePO.setUpdateUserId(0L);
//        helpCenterArticlePO.setUpdateTime(LocalDateTime.now());
//        HelpCenterArticlePO save = helpCenterArticleRepository.save(helpCenterArticlePO);

        Optional<HelpCenterArticlePO> byId = helpCenterArticleRepository.findById("aZTA-3MBQ5_9CYD75vOq");
        System.out.println(byId.get());
    }


//    public static void main(String[] args) throws Exception {
////
////        String[] fields = {"sex", "name"};
////        TermVectorsResponse response = termVectors("yiwise", "test", "0Mc6S3IBQ5_9CYD7bU76", fields);
////        // 是否有找到匹配的
////        String index = response.getIndex();
////        String type = response.getType();
////        String id = response.getId();
////        System.out.println(index + "===" + id);
////        boolean found = response.getFound();
////        if (found) {
////            for (TermVectorsResponse.TermVector tv : response.getTermVectorsList()) {
////                String fieldname = tv.getFieldName();
////                Integer docCount = tv.getFieldStatistics().getDocCount();
////                Long sumTotalTermFreq = tv.getFieldStatistics().getSumTotalTermFreq();
////                Long sumDocFreq = tv.getFieldStatistics().getSumDocFreq();
////                if (tv.getTerms() != null) {
////                    List<TermVectorsResponse.TermVector.Term> terms = tv.getTerms();
////                    for (TermVectorsResponse.TermVector.Term term : terms) {
////                        String termStr = term.getTerm();
////                        Integer termFreq = term.getTermFreq();
////                        Integer docFreq = term.getDocFreq();
////                        Long totalTermFreq = term.getTotalTermFreq();
////                        Float score = term.getScore();
////                        if (term.getTokens() != null) {
////                            List<TermVectorsResponse.TermVector.Token> tokens = term.getTokens();
////                            for (TermVectorsResponse.TermVector.Token token : tokens) {
////                                Integer position = token.getPosition();
////                                Integer startOffset = token.getStartOffset();
////                                Integer endOffset = token.getEndOffset();
////                                String payload = token.getPayload();
////                                System.out.println(position + " " + startOffset + " " + endOffset + " " + payload);
////                            }
////                        }
////                    }
////                }
////            }
////        }
//
//    }

    @Resource
    private PersonRepository personRepository;
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void test() {
        System.out.println("======");
        boolean b = elasticsearchRestTemplate.indexExists(PersonPO.class);
        System.out.println(b);
        boolean index = elasticsearchRestTemplate.createIndex(PersonPO.class);
        System.out.println(index);
//        boolean index2 = elasticsearchRestTemplate.createIndex(ChildrenPO.class);
//        System.out.println(index2);

    }

    /**
     * 创建document
     * @return
     */
    @Test
    public void createDocument() {

//        PersonPO personPO = new PersonPO();
//        personPO.setName("娃哈哈");
//        personPO.setSex("男");
//        personPO.setAddress("中国浙江省杭州市余杭区"+new Random().nextLong());
//        personPO.setBirthDay(LocalDateTime.now());
//        personPO.setEmail(new Random().nextLong() +"@qq.com");
//        personPO.setSort(new Random().nextInt()%39);
//        PersonPO save = personRepository.save(personPO);
//        System.out.println(save);

        List<PersonPO> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PersonPO personPO = new PersonPO();
            personPO.setName("人才"+i);
            personPO.setSex(i%2==0?"男":"女");
            personPO.setAddress("中国浙江省杭州市萧山区"+new Random().nextLong());
            personPO.setBirthDay(LocalDateTime.now().minusDays(i));
            personPO.setEmail(new Random().nextLong() +"@qq.com");
            personPO.setSort(i%39);
            list.add(personPO);
//            PersonPO save = personRepository.save(personPO);
//            System.out.println(save);
        }
        Iterable<PersonPO> personPOS = personRepository.saveAll(list);
        personPOS.forEach(personPO -> {
            System.out.println(personPO);
        });

    }
    /**
     * 获取document
     * @return
     */
    @Test
    public void getDocument() {
        Optional<PersonPO> optional = personRepository.findById("n5Tm-nMBQ5_9CYD7zpRI");
        System.out.println(optional.get());
    }

    /**
     * 是否存在这个document
     * @return
     */
    @Test
    public void existsDocument() {
        boolean exitById = personRepository.existsById("n5Tm-nMBQ5_9CYD7zpRI");
        System.out.println(exitById);
    }
    /**
     * 删除document
     *
     */
    @Test
    public void deleteDocument() {
        personRepository.deleteById("n5Tm-nMBQ5_9CYD7zpRI");
    }

    /**
     * 修改document
     *
     * @return
     */
    @Test
    public void updateDocument() {
        PersonPO personPO = new PersonPO();
        personPO.setId("n5Tm-nMBQ5_9CYD7zpRI");
        personPO.setName("天才");
        personPO.setSex("男");
        personPO.setAddress("中国浙江省杭州市萧山区123123123"+new Random().nextLong());
        personPO.setBirthDay(LocalDateTime.now());
        personPO.setEmail(new Random().nextLong() +"@qq.com");
        personPO.setSort(new Random().nextInt()%39);
        PersonPO save = personRepository.save(personPO);
        System.out.println(save);

    }

    /**
     * 文档统计
     * @return
     */
    @Test
    public void termVectors() {
//        TermVectorsRequest request = new TermVectorsRequest(key, type, id);
//        request.setFields(field);
//        try {
//            TermVectorsResponse response = restHighLevelClient.termvectors(request, RequestOptions.DEFAULT);
//            System.out.println(response);
//        } catch (IOException e) {
//            log.error("termVectors错误", e);
//        }


//        QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("address", "萧山区"));
//
//        Page<PersonPO> search = personRepository.search(query, PageRequest.of(0, 10));
//
//        Iterable<PersonPO> sort = personRepository.findAll(Sort.by(Sort.Direction.DESC, "sort"));
//
//        System.out.println(sort);


        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 添加基本的分词查询
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", "人才"));
        BoolQueryBuilder boolQueryBuilder2 = QueryBuilders.boolQuery();
        boolQueryBuilder2.should(QueryBuilders.matchQuery("address", "萧山区"));
        boolQueryBuilder2.should(QueryBuilders.matchQuery("address", "余杭区"));
        boolQueryBuilder.must(boolQueryBuilder2);
        queryBuilder.withQuery(QueryBuilders.matchQuery("name", "人才"));

        queryBuilder.withPageable(PageRequest.of(0, 50));

        queryBuilder.withSort(SortBuilders.fieldSort("sex").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("sort").order(SortOrder.DESC));

        queryBuilder.withHighlightFields(new HighlightBuilder.Field("name"));

        Page<PersonPO> search = personRepository.search(queryBuilder.build());

        search.getContent().forEach(personPO -> {
            System.out.println(personPO);
        });

//        System.out.println("=======");
//
//        List<PersonPO> personPOS = elasticsearchRestTemplate.queryForList(queryBuilder.build(), PersonPO.class);
//        personPOS.forEach(personPO -> {
//            System.out.println(personPO);
//        });
//
//        System.out.println("=======");
//
//        Page<PersonPO> personPage = elasticsearchRestTemplate.queryForPage(queryBuilder.build(), PersonPO.class);
//        personPage.forEach(personPO -> {
//            System.out.println(personPO);
//        });



    }

}
