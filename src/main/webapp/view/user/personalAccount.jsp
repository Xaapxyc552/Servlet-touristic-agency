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
<fmt:message key="personal_page.money"/>
<%--<@commonMacro.showMoneyByLocale money "${requestContext.locale}" dollarCourse/><br>--%>
<br>
<form action="/user/recharge" method="post">
    <%--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
    <fmt:message key="personal_page.recharge"/><br>
    <label for="amountOfCharge"><input type="number" id="amountOfCharge" name="amountOfCharge"></label>
    <input type="submit" value="<fmt:message key="personal_page.recharge"/>">
</form>


<%--<@commonMacro.pagingMacro pagesSequence=pagesSequence currentPage=currentPage  href="/user/personal-account/"/>--%>

<table>
    <fmt:message key="check.header"/> <br>
    <c:forEach var="check" items="${requestScope.get('checkToDisplay')}">
        <tr>
            <th><fmt:message key="check.tour_name"/></th>
            <c:if test="${language=='uk-ua'}">
                <td>${check.tour.name["uk_UA"]}</td>
            </c:if>
            <c:if test="${language=='en-gb'}">
                <td>${check.tour.name["en_GB"]}</td>
            </c:if>
        </tr>
        <tr>
            <th><fmt:message key="check.total_price"/></th>
            <th>
                    <%--            <@commonMacro.showMoneyByLocale check.totalPrice "${requestContext.locale}" dollarCourse/>--%>
            </th>
        </tr>
        <tr>
            <th><fmt:message key="check.status"/></th>
            <th><fmt:message key="check.status.${check.status.status}"/></th>
        </tr>
        <c:if test="${check.status.status.name()=='WAITING_FOR_CONFIRM'}">
            <tr>
                <th>
                    <form action="/app/user/remove-booking" method="post">
                            <%--                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                        <input type="hidden" name="check_id" value="${check.id}"/>
                        <input type="submit" value="<fmt:message key="check.remove"/>">

                    </form>
                </th>
            </tr>
        </c:if>
        <tr>
            <th><br></th>
        </tr>
        <%--<#else> <fmt:message key="check.you_dont_have_bookings_yet"/>--%>
    </c:forEach>
</table>
<#--//счет,имя, роль, ссылки на другие места-->