package com.zipsoft.member;

import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.member.dto.MemberDto;
import com.zipsoft.member.dto.ThumbnailDto;

public interface MemberService {
	
	public UserDto detail(long userId);
	
	public void edit(MemberDto member);
	
	public ThumbnailDto getThumbnail(long id);
	
}
