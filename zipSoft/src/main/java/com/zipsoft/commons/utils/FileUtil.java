package com.zipsoft.commons.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.zipsoft.enums.FilePath;
import com.zipsoft.model.FileDto;

@Component
public class FileUtil {
	
	private static String root;
	
	@Value("${file.location}")
	public void setRoot(String value) {
		root = value;
	}
	
	public static FileDto upload(MultipartFile m, FilePath filePath) {
		if (m == null || m.isEmpty()) return null;
			
		try {
			String orgFileName = m.getOriginalFilename();
			String extension = orgFileName.substring(orgFileName.lastIndexOf(".") + 1);
			String fileName = UUID.randomUUID().toString() + "." + extension;
			
			int size = Long.valueOf(m.getSize()/1000).intValue();
			
			Path p = Paths.get(root + filePath.getPath() + File.separator + fileName);
			Files.createDirectories(p);
			Files.copy(m.getInputStream(), p, StandardCopyOption.REPLACE_EXISTING);
			
			return FileDto.builder()
						  .orgFileName(orgFileName)
						  .fileName(fileName)
						  .ext(extension)
						  .size(size)
						  .path(filePath.getPath())
						  .build();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void download() {
		
	}
	
}
