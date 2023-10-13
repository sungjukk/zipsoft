package com.zipsoft.friend;

import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Repository;

import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.elasticsearch.ElasticSearchRepository;

@Repository
public class FriendElasticRepository extends ElasticSearchRepository<Long, UserDto> {

}
