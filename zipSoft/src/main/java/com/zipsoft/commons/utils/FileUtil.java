package com.zipsoft.commons.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.zipsoft.enums.FilePath;
import com.zipsoft.model.FileDto;

import jakarta.servlet.http.HttpServletRequest;

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
	
	public static ResponseEntity<Resource> download(HttpServletRequest request,String fileName, String orgFileName, String ext, FilePath filePath) {
		try {
			Path p = Paths.get(root + filePath.getPath() + File.separator + fileName);
			Resource resource = new FileSystemResource(p);
			
			if (!resource.exists()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			
			String encodedFileName = null;
	        String contentType = null;
	        
	        contentType = Files.probeContentType(p);
	        encodedFileName = URLEncoder.encode(orgFileName,"UTF-8").replaceAll("\\+", "%20");
	        
	        if (contentType == null || "".equals(contentType)) contentType = "application/octet-stream";
			
	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
	                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(Files.size(p)))
	                .body(resource);
	        
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); 
		} 
		
		
	}
	
	public static ResponseEntity<Resource> imageViewer(FilePath filePath, String fileName) {
		try {
			Path p = Paths.get(root + filePath.getPath() + File.separator + fileName);
			Resource resource = new FileSystemResource(p);
			
			if (!resource.exists()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			
			String encodedFileName = null;
	        String contentType = null;
	        
	        contentType = Files.probeContentType(p);
	        
	        if (contentType == null || "".equals(contentType)) contentType = "application/octet-stream";
			
	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .body(resource);
	        
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); 
		} 
	}
	
	public static boolean deleteFile(FilePath filePath, String fileName) {
		try {
			Path p = Paths.get(root + filePath.getPath() + File.separator + fileName);
			Files.deleteIfExists(p);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
}
