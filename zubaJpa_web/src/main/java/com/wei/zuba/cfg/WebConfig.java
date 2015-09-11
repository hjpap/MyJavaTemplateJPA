package com.wei.zuba.cfg;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wei.zuba.inteceptor.BusinessInterceptor;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new MyCustomizer();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(businessInterceptor()).addPathPatterns("/index/aaa");
		//registry.addInterceptor(customerOperateInterceptor()).addPathPatterns("/account/recharge","/account/cerRecharge", "/account/withdraw", "/account/toRedeem", "/loan/buyProduct",
		//		"/account/identification", "/register/settingPwd", "/account/safe", "/account/user","/register/register2");
		//registry.addInterceptor(businessInterceptor()).addPathPatterns("/account/recharge", "/account/withdraw","/account/cerRecharge");
	}

//	@Bean
//	public HandlerInterceptor requestAccessInterceptor() {
//		RequestAccessInterceptor interceptor = new RequestAccessInterceptor();
//		return interceptor;
//	}
//
//	@Bean
//	public CustomerOperateInterceptor customerOperateInterceptor() {
//		return new CustomerOperateInterceptor();
//	}
//
	@Bean
	public BusinessInterceptor businessInterceptor() {
		return new BusinessInterceptor();
	}

//	@Bean
//	public FilterRegistrationBean siteMeshFilter() {
//		Filter filter = new SiteMeshFilter();
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		registrationBean.setFilter(filter);
//		registrationBean.setUrlPatterns(Arrays.asList("/*"));
//		return registrationBean;
//	}

	private static class MyCustomizer implements EmbeddedServletContainerCustomizer {

		@Override
		public void customize(ConfigurableEmbeddedServletContainer container) {
			container.addErrorPages(new ErrorPage(Throwable.class, "/WEB-INF/views/error/error.jsp"));
			container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/WEB-INF/views/error/error.jsp"));
			container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/views/error/error.jsp"));
			container.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/WEB-INF/views/error/error.jsp"));
			container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/WEB-INF/views/error/error.jsp"));
		}
	}

	// @Bean
	// public MappingJackson2HttpMessageConverter
	// customJackson2HttpMessageConverter() {
	// MappingJackson2HttpMessageConverter jsonConverter = new
	// MappingJackson2HttpMessageConverter();
	// ObjectMapper objectMapper = new ObjectMapper();
	// objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
	// false);
	// jsonConverter.setObjectMapper(objectMapper);
	// return jsonConverter;
	// }
	//
	// @Override
	// public void configureMessageConverters(List<HttpMessageConverter<?>>
	// converters) {
	// converters.add(customJackson2HttpMessageConverter());
	// super.configureMessageConverters(converters);
	// }

}