package com.wei.zuba.cfg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import com.wei.zuba.utils.RestClient;


@ConfigurationProperties(prefix = "rest", ignoreUnknownFields = false)
@Configuration
public class RestClientConfig {
	
	@Getter
	@Setter
	private Client client;
	
	@Getter
	@Setter
	public static class Client {
		String appSource;
		int readTimeout;
		int connectTimeout;
		String servicePrefix;
		String  md5Seeds;
	}
	
	@Getter
	@Setter
	private String smsServicePrefix;
	
	@Getter
	@Setter
	int maxConnection;
	@Getter
	@Setter
	int maxConnectionPerRoute;
	
	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory simpleClientHttpRequestFactory  = new HttpComponentsClientHttpRequestFactory(httpClient());
		simpleClientHttpRequestFactory.setReadTimeout(client.readTimeout);
		simpleClientHttpRequestFactory.setConnectTimeout(client.connectTimeout);
		return simpleClientHttpRequestFactory;
	}
	
	@Bean
	public HttpClient httpClient() {
		HttpClient client =  HttpClients.custom().setMaxConnTotal(maxConnection).setMaxConnPerRoute(maxConnectionPerRoute).build();
		return client;
	}
	
	@Bean
	public RestClient restClient() {
		RestClient restClient = new RestClient(simpleClientHttpRequestFactory());
		 List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		 configureMessageConverters(messageConverters);
		 restClient.setMessageConverters(messageConverters);
		 restClient.setRequestFactory(simpleClientHttpRequestFactory());
		 restClient.setErrorHandler(new ResponseErrorHandler() {
			 private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return errorHandler.hasError(response);
			}
			
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				  List<String> customHeader = response.getHeaders().get("x-slb-err");

			        String svcErrorMessageID = null;
			        if (customHeader != null) {
			            svcErrorMessageID = new String(BaseEncoding.base64().decode(customHeader.get(0)), "UTF-8");                
			        }

			        try {           
			            errorHandler.handleError(response);
			        } catch (RestClientException scx) {         
			        	if(svcErrorMessageID != null)
			        		throw new RuntimeException(svcErrorMessageID);
			        	
			        	throw scx;
			        }
				
			}
		});
		//restClient.setPrefix(client.servicePrefix);
		return restClient;
	}
	
	private ObjectMapper objectMapper() {
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.registerModule(new JodaModule());
	    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	    //objectMapper.setDateFormat(new ISO8601DateFormat());
	    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	    objectMapper.registerModule(new Jackson2HalModule());
	    return objectMapper;
	}

	public void configureMessageConverters(
	        List<HttpMessageConverter<?>> messageConverters) {
	    MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
	    jsonMessageConverter.setObjectMapper(objectMapper());
	    jsonMessageConverter.setSupportedMediaTypes(MediaType
	            .parseMediaTypes("application/hal+json,application/json"));
	    
	    StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charsets.UTF_8);
	    messageConverters.add(jsonMessageConverter);
	    messageConverters.add(stringHttpMessageConverter);
	}
}
