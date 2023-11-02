package com.zipsoft.auth.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.zipsoft.elasticsearch.dto.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "test_chosung_data")
public class UserDto implements Serializable {
	private long id;
	private String userId;
	private String password;
	private String email;
	private String userName;
	private long thumbId;
}
