<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<c:set scope="page" var="language" value="${cookie.get('language').value}"/>

<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="messages"/>


<!DOCTYPE html>
<html lang="${language}">
<head>
    <title></title>
</head>
<body>
<jsp:include page="common/common.jsp"/>
<br>
<h1>${requestScope.get('localizedMessage')}</h1>

</table>
</body>
</html>

