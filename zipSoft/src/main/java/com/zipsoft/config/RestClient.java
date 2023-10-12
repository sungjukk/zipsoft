package com.zipsoft.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestClient {
	
	@Value("${elasticsearch.schema}")
	private String schema;
	
	@Value("${elasticsearch.host}")
	private String elasticsearchHostName;
	
	@Value("${elasticsearch.port}")
	private int elasticsearchPort;
	
	@Value("${elasticsearch.username}")
	private String userName;
	
	@Value("${elasticsearch.password}")
	private String password;
	
	@Bean
	public org.elasticsearch.client.RestClient createClient() {
		
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
		
		RestClientBuilder builder = org.elasticsearch.client.RestClient.builder(new HttpHost(elasticsearchHostName, elasticsearchPort, schema))
																	   .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
																		
																		@Override
																		public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
																			return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
																		}
																	});
		
		return builder.build();
	}
	
}
