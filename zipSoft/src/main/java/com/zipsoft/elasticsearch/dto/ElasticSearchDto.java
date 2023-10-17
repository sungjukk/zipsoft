package com.zipsoft.elasticsearch.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class ElasticSearchDto {
	
	public enum SORT_TYPE {
		ASC, DESC
	}
	
	private int page;
	private int from;
	private int size;
	private List<Sort> sorts;
	private ConditionalStatement asd;
	
	public ElasticSearchDto() {
		this.page = 0;
		this.size = 0;
		this.sorts = new ArrayList<>();
	}
	
	public void sort(String field) {
		sorts.add(new Sort(field, SORT_TYPE.ASC));
	}
	
	public void sort(String field, SORT_TYPE type) {
		sorts.add(new Sort(field, type));
	}
	
	public String toQueryString() {
		this.from = (page - 1) * size;
		
		String json = "{";
		
		if (this.page > 0) json += "\"from\" : " + this.from;
		if (this.size > 0) {
			json += (!json.equals("{") ? "," : "") + "\"size\" : " + this.size;
		}
		
		if (!this.sorts.isEmpty()) {
			JSONArray arr = new JSONArray();
			for (Sort s : sorts) {
				JSONObject sObj = new JSONObject();
				sObj.put(s.getColumn(), s.getSort().name().toLowerCase());
				arr.add(sObj);
			}
			json += (!json.equals("{") ? "," : "") + "\"sort\" : " + arr.toJSONString();
		}
		
		if (asd != null) {
			json += (!json.equals("{") ? "," : "") + """
					"query" :{
					        "query_string" : {
					            "query" : """ + "\"" + asd.getTxt() + "\"" + """
					        }
					    }
					""";
		}
		
		json +="}";
		
		return json;
	}
	
	
	@Data
	public static class Sort {
		private SORT_TYPE sort;
		private String column;
		
		public Sort(String column, SORT_TYPE sort) {
			this.column = column;
			this.sort = sort;
		}
	}
	
	
}
