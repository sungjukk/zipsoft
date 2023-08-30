package com.zipsoft.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.zipsoft.commons.utils.CustomPageRequest;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class PageParams {
	
	private int size;
	private int page;
	private String sort;
	private String orderType;
	
	
	public Pageable of() {
		CustomPageRequest page = new CustomPageRequest();
		page.setSize(this.size);
		page.setPage(this.page);
		if (sort != null && !"".equals(sort)) 
			page.setDirection("asc".equals(sort) ? Direction.ASC : Direction.DESC);
		
		if (orderType != null && !"".equals(orderType)) return page.of(orderType);
		
		return page.of();
	}
}
