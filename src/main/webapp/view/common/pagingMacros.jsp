<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie.get('language').value}" scope="session"/>
<fmt:setBundle basename="messages"/>
<table>
    <tr>
        <c:forEach items="${requestScope.get('pagesSequence')}" var="page">
            <c:choose>
                <c:when test="${page != sessionScope.get('currentPage')}">
                    <td>  <form action="${hrefForPagination}">
                        <input type="hidden" name="currentPage" value="${page}"/>
                        <input type="submit" value="${page+1}">
                    </form>
                    </td>
                </c:when>
                <c:when test="${page == sessionScope.get('currentPage')}">
                    <td>
                            ${page+1}
                    </td>
                </c:when>
            </c:choose>

        </c:forEach>
    </tr>
</table>

