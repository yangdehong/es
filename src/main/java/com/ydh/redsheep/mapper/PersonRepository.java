package com.ydh.redsheep.mapper;

import com.ydh.redsheep.model.entity.PersonPO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PersonRepository extends ElasticsearchRepository<PersonPO, String> {
}
