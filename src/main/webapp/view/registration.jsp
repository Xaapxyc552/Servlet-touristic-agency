<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie.get('language').value}" scope="session" />
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${cookie.get('language').value}" >
<head>
    <title></title>

</head>
<body>
<jsp:include page="common/common.jsp"/>
    <a href="${pageContext.request.contextPath}/view/index.jsp">Index</a>
    <fmt:message key="message.user.registration.page.header"/>
    <h1>
        <fmt:message key="form.header"/>
    </h1>

<c:if test="${requestScope.get('constraintViolations') != null}">
    <c:forEach var="constraintVioation" items="${requestScope.get('constraintViolations')}">
        <p><c:out value="${constraintVioation}"/></p>
    </c:forEach>
</c:if>

<c:if test="${requestScope.get('userRegistrationMessage') == true}">
    <fmt:message key="user.registered"/>
</c:if>

<c:if test="${requestScope.get('userRegistrationMessage') == false}">
    <fmt:message key="user.not_registered"/>
</c:if>

    <form action="${pageContext.request.contextPath}/app/register-user" method="post">
        <%--            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
        <h1></h1>
        <table>
            <tr>
                <td><label for="firstname"><fmt:message key="form.firstname"/></label><br>
                    <input type="text" id="firstname" name="firstname">
                </td>
            </tr>
            <tr>
                <td><label for="username"><fmt:message key="form.username"/></label><br>
                    <input type="text" id="username" name="username">
                </td>
            </tr>
            <tr>
                <td><label for="email"><fmt:message key="form.email"/></label><br>
                    <input type="email" id="email" name="email">
                </td>
            </tr>
            <tr>
                <td><label for="password"><fmt:message key="form.password"/></label><br>
                    <input type="password" id="password" name="password">
                </td>
            </tr>
            <tr>
                <td><input type="submit">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>