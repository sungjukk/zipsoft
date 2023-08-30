package com.zipsoft.board.dto;

import com.zipsoft.model.PageParams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SearchDto extends PageParams {
	
	private String subject;
	private String regName;
	
}
