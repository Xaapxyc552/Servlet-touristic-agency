<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie.get('language').value}" scope="session"/>
<fmt:setBundle basename="messages"/>


<table border="3">
    <tr>
        <td>
            <form action="${pageContext.request.contextPath}/app/locale" method="get">
                <input type="hidden" name="language" value="en-GB">
                <input type="submit" value="English">
            </form>
            <form action="${pageContext.request.contextPath}/app/locale" method="get">
                <input type="hidden" name="language" value="uk-UA">
                <input type="submit" value="Українська">
            </form>
        </td>
    </tr>
    <tr>
        <td><c:choose>
            <c:when test="${sessionScope.get('role') == null}">
                <a href="${pageContext.request.contextPath}/app/login"><fmt:message key="common.head.login"/></a>
                <a href="${pageContext.request.contextPath}/app/registration"><fmt:message
                        key="common.head.sign_up"/></a>
            </c:when>
            <c:when test="${sessionScope.get('role') == 'ROLE_USER'}">
                <fmt:message key="common.you_login_as"/> ${sessionScope.username}
                <a href="${pageContext.request.contextPath}/app/user/personal-account"><fmt:message
                        key="common.personal_account"/></a><br>
                <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="common.logout"/></a><br>
            </c:when>
            <c:when test="${sessionScope.get('role') == 'ROLE_MANAGER'}">
                <fmt:message key="common.you_login_as"/> ${sessionScope.username}
                <a href="${pageContext.request.contextPath}/app/manager/tours-operations"><fmt:message
                        key="common.manager_page"/></a><br>
                <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="common.logout"/></a><br>
            </c:when>
            <c:when test="${sessionScope.get('role') == 'ROLE_ADMIN'}">
                <fmt:message key="common.you_login_as"/> ${sessionScope.username}
                <a href="${pageContext.request.contextPath}/app/manager/tours-operations"><fmt:message
                        key="common.manager_page"/></a><br>
                <a href="${pageContext.request.contextPath}/app/admin/new-tour"><fmt:message key="common.new_tour"/></a><br>
                <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="common.logout"/></a><br>
            </c:when>
        </c:choose>
        </td>
    </tr>
    <tr>
        <td><h3><fmt:message key="common.navigation.header"/></h3>
            <a href="${pageContext.request.contextPath}/app/display-tours"><fmt:message key="common.navigation.tours"/></a><br>
        </td>
    </tr>
    <table/>


