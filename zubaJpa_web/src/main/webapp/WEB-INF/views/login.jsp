<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
<head>
<title>login</title>

</head>
<body>
<div>
	
		<c:set var="info" value="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}"/>
		<c:if test="${fn:indexOf(info,'Authentication method not supported')=='-1'}">
			${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
		</c:if>    
</div>		        
<form method="post" action="/loginPost">
	<input type="text" name="loginName">
	<input type="password" name="password">
	<input type="submit" value="登陆"/>
	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

</body>
</html>