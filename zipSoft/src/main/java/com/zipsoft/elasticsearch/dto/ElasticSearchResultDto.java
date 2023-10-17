package com.zipsoft.elasticsearch.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.zipsoft.commons.utils.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElasticSearchResultDto<T> {
	private int took;
    @JsonProperty("timed_out")
    private boolean timedOut;
    @JsonProperty("_shards")
    private ShardsDto shards;
    private HitsDto<T> hits;
    private List<T> items;
    
    public List<T> getItems() {
    	if (this.hits != null) {
    		if (this.hits.hits != null && !this.hits.hits.isEmpty()) {
    			return this.hits.hits.stream().map(s -> s.getSource()).collect(Collectors.toList());    			
    		}
    	}
    	return new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    public static class ShardsDto {
        private int total;
        private int successful;
        private int skipped;
        private int failed;
    }

    @Data
    @NoArgsConstructor
    public static class HitsDto<T> {
        private HitsTotalDto total;
        @JsonProperty("max_score")
        private Float maxScore;
        private List<HitsItemDto<T>> hits;
    }

    @Data
    @NoArgsConstructor
    public static class HitsTotalDto {
        private int value;
        private String relation;
    }

    @Data
    @NoArgsConstructor    
	public static class HitsItemDto<T> {
        @JsonProperty("_index")
        private String index;
        @JsonProperty("_id")
        private String id;
        @JsonProperty("_score")
        private Float score;
        @JsonProperty("_ignored")
        private String[] ignored;
        @JsonProperty("_source")
        private T source;
        
    }
}
