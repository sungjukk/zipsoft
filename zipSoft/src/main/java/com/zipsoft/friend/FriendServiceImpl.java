package com.zipsoft.friend;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.commons.utils.CommonUtil;
import com.zipsoft.elasticsearch.dto.ConditionalStatement;
import com.zipsoft.elasticsearch.dto.ElasticSearchDto;
import com.zipsoft.elasticsearch.dto.ElasticSearchResultDto;
import com.zipsoft.friend.dto.FriendDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
	
	private final FriendRepository friendRepository;
	
	private final FriendElasticRepository friendElasticRepository;
	
	@Override
	public List<FriendDto> list(long userId) {
		return friendRepository.list(userId);
	}

	@Override
	public List<UserDto> search(String userName, long userId) {
		
		String searchType = CommonUtil.isChosung(userName) ? "chosung" : "jamo";
		
		ConditionalStatement con = ConditionalStatement.builder().field("user_name").eq(userName)
																 .and()
																 .field("userId").ne(String.valueOf(99))
																 .Build();
		
		ElasticSearchDto search = ElasticSearchDto.builder().asd(con).build();
		
		ElasticSearchResultDto<UserDto> result = friendElasticRepository.search(search);
		return result.getItems();
	}

	@Override
	public FriendDto detail(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
