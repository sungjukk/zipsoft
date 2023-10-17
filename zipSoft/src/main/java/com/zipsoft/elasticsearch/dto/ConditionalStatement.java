package com.zipsoft.elasticsearch.dto;

import com.zipsoft.elasticsearch.dto.ConditionalStatement.Builder;

import lombok.Data;

@Data
public class ConditionalStatement {
	
	private String txt;
	
	public ConditionalStatement(String txt) {
		this.txt = txt;
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private String field;
		private String txt;
		
		public Builder() {
			this.txt = "";
		}
		
		public Builder field(String field) {
			this.field = field;
			return this;
		}
		
		public Builder eq(String keyword) {
			this.txt += this.field + ":" + keyword;
			return this;
		}
		
		public Builder ne(String keyword) {
			this.txt += "!(" + this.field + ":" + keyword + ")";
			return this;
		}
		
		public Builder range(int min, int max) {
			this.txt += this.field + ":" + "(>" + min + " AND " + "<" + max + ")";
			return this;
		}
		
		public Builder and() {
			this.txt += " AND ";
			return this;
		}
		
		public Builder or() {
			this.txt += " OR ";
			return this;
		}
		
		public ConditionalStatement Build() {
			return new ConditionalStatement(this.txt);
		}
	}

}
