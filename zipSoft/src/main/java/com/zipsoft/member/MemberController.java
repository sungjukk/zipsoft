package com.zipsoft.member;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.commons.payload.ApiResponse;
import com.zipsoft.commons.security.UserPrincipal;
import com.zipsoft.commons.utils.FileUtil;
import com.zipsoft.enums.FilePath;
import com.zipsoft.member.dto.MemberDto;
import com.zipsoft.member.dto.ThumbnailDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("/edit")
	public ApiResponse detail(@AuthenticationPrincipal UserPrincipal user) {
		return ApiResponse.OK(memberService.detail(user.getUserId()));
	}
	
	@PostMapping("/edit")
	public ApiResponse edit(MemberDto member, @AuthenticationPrincipal UserPrincipal user) {
		member.setId(user.getUserId());
		memberService.edit(member); 
		
		return ApiResponse.OK(null);
	}
	
	@GetMapping("/thumbnail/{id}")
	public ResponseEntity<Resource> editorImageViewer(HttpServletRequest request, @PathVariable("id") long id) {
		
		ThumbnailDto dto = memberService.getThumbnail(id);
		
		return FileUtil.imageViewer(FilePath.USER_IMAGE, dto.getThumbFileName());
	}
	
	
}
