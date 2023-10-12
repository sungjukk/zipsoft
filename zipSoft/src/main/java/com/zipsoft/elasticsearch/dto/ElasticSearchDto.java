package com.zipsoft.elasticsearch.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElasticSearchDto {
	
	public enum SORT_TYPE {
		ASC, DESC
	}
	
	private int page;
	private int size;
	private List<Sort> sorts;
	
	
	@Data
	public static class Sort {
		private SORT_TYPE sort;
		private String column;
	}
	
	public static class Condition {
		private String field;
		private String txt = "";
		
		public Condition field(String field) {
			this.field = field;
			return this;
		}
		
		public Condition eq(String keyword) {
			this.txt += this.field + ":" + keyword;
			return this;
		}
		
		public Condition range(int min, int max) {
			this.txt += this.field + ":" + "(>" + min + " AND " + "<" + max + ")";
			return this;
		}
		
		public Condition and() {
			this.txt += " AND ";
			return this;
		}
		
		public String getTxt() {
			return this.txt;
		}
	}
	
}
