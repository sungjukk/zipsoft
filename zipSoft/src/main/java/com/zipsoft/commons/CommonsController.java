package com.zipsoft.commons;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zipsoft.commons.payload.ApiResponse;
import com.zipsoft.commons.utils.FileUtil;
import com.zipsoft.enums.FilePath;
import com.zipsoft.model.FileDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
public class CommonsController {
	
	@PostMapping("/upload/editor/image")
	public ApiResponse uploadEditorImage(MultipartFile[] files) {
		List<String> list = new ArrayList<>();
		
		if (files != null) {
			
			
			for (MultipartFile file : files) {
				FileDto f = FileUtil.upload(file, FilePath.EDITOR_IMAGE);
				list.add(f.getFileName());
			}
		}
		
		return ApiResponse.OK(list);
	}
	
	@GetMapping("/editor/imageView")
	public ResponseEntity<Resource> editorImageViewer(HttpServletRequest request, String fileName) {
		return FileUtil.imageViewer(FilePath.EDITOR_IMAGE, fileName);
	}
	
}
