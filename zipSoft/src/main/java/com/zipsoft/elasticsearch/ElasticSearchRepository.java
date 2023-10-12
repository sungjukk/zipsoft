package com.zipsoft.elasticsearch;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Repository;

import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.commons.utils.CommonUtil;
import com.zipsoft.elasticsearch.dto.Document;
import com.zipsoft.elasticsearch.dto.ElasticSearchResultDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public abstract class ElasticSearchRepository<PK extends Serializable, T> {
	
	private final RestClient restClient;
	
	private final Class<T> preClass;
	
	public ElasticSearchResultDto search(String keyword) {
		
		String index = this.getIndexName();
		if ("".equals(index) || index == null) return null;
		
		String url = "/" + index + "_search";
		String json = "";
		
		if (!"".equals(keyword) && keyword != null) {
			json = """
					{
					    "query" :{
					        "query_string" : {
					            "query" : "user_name:""" + keyword + "\"";
			
			json +=	"} } }";
		}
		
		String result = this.connectElasticSearch("GET", url, json);
		
		if (result != null) {
			ElasticSearchResultDto dto = CommonUtil.jsonToDtoGeneric(result, ElasticSearchResultDto.class, preClass);
			return dto;
		}
		
		
		return null;
	}
	
	public T getId(PK id) {
		String index = this.getIndexName();
		String type = this.getTypeName();
		String url = "/" + index + "/" + type + "/" + id;
		
		String result = this.connectElasticSearch("GET", "/" + url, "");
		
		if (result != null) {
			Map<String, Object> map = CommonUtil.jsonToDto(result, Map.class);
			Object obj = map.get("_source");
			return CommonUtil.ObjectToDto(obj, preClass);
			
		} else {
			return null;
		}
		
	}
	
	private String connectElasticSearch(String type, String url, String json) {
		Request request = new Request(type, url);
		
		if (!"".equals(json) && json != null) {
			HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
			request.setEntity(entity);
		}
		
		Response response;
		try {
			response = restClient.performRequest(request);
			String result = EntityUtils.toString(response.getEntity());
			
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	private String getIndexName() {
		Document document = preClass.getAnnotation(Document.class);
		return document.indexName();
	}
	
	private String getTypeName() {
		Document document = preClass.getAnnotation(Document.class);
		return document.typeName();
	}
	
}
