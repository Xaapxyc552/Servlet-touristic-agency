<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie.get('language').value}" scope="session"/>
<fmt:setBundle basename="messages"/>

<html lang="${cookie.get('language').value}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login Page</title>
</head>
<body>
<jsp:include page="common/common.jsp"/>

<c:if test="${requestScope.get('loginUnsuccessful')==true}">
    <font color="red"><fmt:message key="login.unsuccessful"/></font>
</c:if>
<h1><fmt:message key="login.header"/></h1><br/>
<form method="post" action="${pageContext.request.contextPath}/app/login">

    <label>
        <fmt:message key="form.username"/> <input type="text" name="username">
    </label><br/>
    <label>
        <fmt:message key="form.password"/> <input type="password" name="password">
    </label><br/><br/>
    <input class="button" type="submit" value="<fmt:message key="login.button"/>">
    </label><br/>
</form>
</body>
</html>