package com.zipsoft.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class ResizeImage implements MultipartFile {
	
	private final byte[] image;
	private final String fileName;
	
	public ResizeImage(byte[] image, String fileName) {
		this.image = image;
		this.fileName = fileName;
	}
	
	public static MultipartFile of(byte[] image, String fileName) {
		return new ResizeImage(image, fileName);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOriginalFilename() {
		// TODO Auto-generated method stub
		return this.fileName;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return image == null || image.length == 0;
	}

	@Override
	public long getSize() {
		// TODO Auto-generated method stub
		return image.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		// TODO Auto-generated method stub
		return image;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(image);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		new FileOutputStream(dest).write(image);
	}

}
