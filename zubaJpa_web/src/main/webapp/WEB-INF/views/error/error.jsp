<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
	Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
	if(statusCode == 200){
		statusCode = 500;
	}
	pageContext.setAttribute("statusCode", statusCode);
	
	String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
	
	// 	    Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
	// 	    request.setAttribute("exception", exception);
	%>
<div class="number">${statusCode} - ${uri } </div>
