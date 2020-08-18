package com.ydh.redsheep.mapper;

import com.ydh.redsheep.model.entity.HelpCenterArticlePO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface HelpCenterArticleRepository extends ElasticsearchRepository<HelpCenterArticlePO, String> {
}
