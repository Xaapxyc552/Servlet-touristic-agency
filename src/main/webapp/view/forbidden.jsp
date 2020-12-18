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
<h1><fmt:message key="page.forbidden"/></h1>
</head>
<body>

</table>
</body>
</html>

