<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:set scope="page" var="language" value="${cookie.get('language').value}"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="messages"/>

<jsp:include page="../common/common.jsp"/>


<c:set var="hrefForPagination" value="/app/manager/tour-operations"/>
<c:set var="currentPage" value="${requestScope.get('currentPage')}"/>
<jsp:include page="../common/pagingMacros.jsp"/>

<table>
<%--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
    <c:forEach items="${requestScope.get('waitingChecks')}" var="check">
        <tr>
            <th><fmt:message key="check.owner_username"/></th>
            <th>${check.user.username}</th>
        </tr>
        <tr>
            <th><fmt:message key="check.owner_email"/></th>
            <th>${check.user.email}</th>
        </tr>
        <tr>
            <th><fmt:message key="check.tour_name"/></th>
            <c:if test="${language=='uk-UA'}">
                <td style="width:200px;">${check.tour.name["uk_UA"]}</td>
            </c:if>
            <c:if test="${language=='en-GB'}">
                <td style="width:200px;">${check.tour.name["en_GB"]}</td>
            </c:if>
        </tr>
        <tr>
            <th><fmt:message key="check.total_price"/></th>
            <th>
                <@commonMacro.showMoneyByLocale check.totalPrice "${requestContext.locale}" dollarCourse/>
            </th>
        </tr>
        <tr>
            <th><fmt:message key="check.status"/></th>
            <th><fmt:message key="check.status.${check.status.status}"/></th>
        </tr>

            <tr>
                <th>
                    <form action="${pageContext.request.contextPath}/app/manager/check-decline" method="post">
                        <input type="hidden" name="check_id" value="${check.id}"/>
                    <%--                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                        <input type="submit" value="<fmt:message key="check.decline"/>">
                    </form>
                </th>
                <th>
                    <form action="${pageContext.request.contextPath}/app/manager/check-confirm" method="post">
                        <input type="hidden" name="check_id" value="${check.id}"/>
                    <%--                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>?--%>
                        <input type="submit" value="<fmt:message key="check.confirm"/>">
                    </form>
                </th>
            </tr>
        <tr>
            <th><br></th>
        </tr>
    </c:forEach>
</table>
