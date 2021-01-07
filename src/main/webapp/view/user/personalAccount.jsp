<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:set scope="page" var="language" value="${cookie.get('language').value}"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="messages"/>


<jsp:include page="../common/common.jsp"/>

<fmt:message key="personal_page.firstname"/>${sessionScope.get('firstname')}<br>
<fmt:message key="personal_page.email"/>${sessionScope.get('email')}<br>
<fmt:message key="personal_page.username"/>${sessionScope.get('username')}<br>
<fmt:message key="personal_page.role"/>${sessionScope.get('role')}<br>
<fmt:message key="personal_page.money"/>${requestScope.get('money')} <fmt:message key="money.sign"/><br>
<c:if test="${isAmountOfChargeNotCorrect=='true'}">
    <fmt:message key="personal_page.amount_of_recharge.error"/>
</c:if>

<form action="${pageContext.request.contextPath}/app/user/recharge-account" method="post">
    <%--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
    <fmt:message key="personal_page.recharge"/><br>
    <label for="amountOfCharge"><input type="number" id="amountOfCharge" name="amountOfCharge"></label>
    <input type="submit" value="<fmt:message key="personal_page.recharge"/>">
</form>


<c:set var="hrefForPagination" value="/app/user/personal-account"/>
<c:set var="currentPage" value="${requestScope.get('currentPage')}"/>
<jsp:include page="../common/pagingMacros.jsp"/>


<table>
    <fmt:message key="check.header"/> <br>
    <c:forEach var="check" items="${requestScope.get('checkToDisplay')}">
        <tr>
            <th><fmt:message key="check.tour_name"/></th>
            <c:if test="${language=='uk-UA'}">
                <td>${check.tourName["uk_UA"]}</td>
            </c:if>
            <c:if test="${language=='en-GB'}">
                <td>${check.tourName["en_GB"]}</td>
            </c:if>
        </tr>
        <tr>
            <th><fmt:message key="check.total_price"/></th>
            <th>
            <td>${check.totalPrice}</td>
            </th>
        </tr>
        <tr>
            <th><fmt:message key="check.status"/></th>
            <th><fmt:message key="check.status.${check.status.status}"/></th>
        </tr>
        <tr>
            <th><fmt:message key="check.booked_time"/></th>
            <th>
                <fmt:parseDate value="${check.creationTime}" pattern="y-M-dd'T'H:m" var="creationTime"/>
                <fmt:formatDate value="${creationTime}" pattern="yyyy.MM.dd HH:mm"/>
            </th>
        </tr>
        <c:choose>
            <c:when test="${check.status.status.name()=='WAITING_FOR_CONFIRM'}">
                <tr>
                    <th>
                        <form action="${pageContext.request.contextPath}/app/user/remove-booking" method="post">
                                <%--                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                            <input type="hidden" name="check_id" value="${check.id}"/>
                            <input type="submit" value="<fmt:message key="check.remove"/>">

                        </form>
                    </th>
                </tr>
            </c:when>
            <c:when test="${check.status.status.name()=='CANCELED'}">
                <tr>
                    <th><fmt:message key="check.canceled_time"/></th>
                    <th>
                        <fmt:parseDate value="${check.lastModificationTime}" pattern="y-M-dd'T'H:m" var="canceledTime"/>
                        <fmt:formatDate value="${canceledTime}" pattern="yyyy.MM.dd HH:mm"/>
                    </th>
                </tr>
            </c:when>
            <c:when test="${check.status.status.name()=='CONFIRMED'}">
                <tr>
                    <th><fmt:message key="check.confirmed_time"/></th>
                    <th>
                        <fmt:parseDate value="${check.lastModificationTime}" pattern="y-M-dd'T'H:m" var="confirmedTime"/>
                        <fmt:formatDate value="${confirmedTime}" pattern="yyyy.MM.dd HH:mm"/>
                    </th>
                </tr>
            </c:when>
            <c:when test="${check.status.status.name()=='DECLINED'}">
                <tr>
                    <th><fmt:message key="check.declined_time"/></th>
                    <th>
                        <fmt:parseDate value="${check.lastModificationTime}" pattern="y-M-dd'T'H:m" var="declinedTime"/>
                        <fmt:formatDate value="${declinedTime}" pattern="yyyy.MM.dd HH:mm"/>
                    </th>
                </tr>
            </c:when>
        </c:choose>
        <tr>
            <th><br></th>
        </tr>
    </c:forEach>
</table>