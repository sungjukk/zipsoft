package com.zipsoft.member;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zipsoft.auth.AuthRepository;
import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.commons.utils.FileUtil;
import com.zipsoft.enums.FilePath;
import com.zipsoft.member.dto.MemberDto;
import com.zipsoft.member.dto.ThumbnailDto;
import com.zipsoft.model.FileDto;
import com.zipsoft.model.entity.User;
import com.zipsoft.model.entity.UserThumbnail;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final AuthRepository authRepository;
	
	private final MemberRepository memberRepository;
	
	@Override
	public UserDto detail(long userId) {
		return authRepository.findById(userId).orElse(null);
	}

	@Override
	@Transactional
	public void edit(MemberDto member) {
		
		memberRepository.update(member);
		
		MultipartFile thumbnail = member.getFile();
		UserThumbnail userThumbnail = null;
		if (thumbnail != null && !thumbnail.isEmpty()) {
			FileDto file = FileUtil.upload(thumbnail, FilePath.USER_IMAGE);
			FileDto thumbFile = FileUtil.resizeImage(thumbnail, FilePath.USER_IMAGE, 120);
			userThumbnail = UserThumbnail.of(file, thumbFile != null ? thumbFile : file, member.getId());
		}
		
		
		memberRepository.updateThumbnailNotUsed(member.getId(), member.getThumbId());
		
		if (userThumbnail != null) memberRepository.insertThumbnail(userThumbnail);
		
		
		
	}

	@Override
	public ThumbnailDto getThumbnail(long id) {
		return memberRepository.getThumbnail(id);
	}

}
