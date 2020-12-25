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
<c:set var="hrefForPagination" value="/app/display-tours"/>
<c:set var="currentPage" value="${sessionScope.get('currentPage')}"/>
<jsp:include page="common/common.jsp"/>
<jsp:include page="common/pagingMacros.jsp"/>

<form action="/app/display-tours" method="get">
    <fmt:message key="tour.form.ordering.header"/>:
    <select name="orderOfTours" id="orderOfTours">
        <option value="AMOUNT_OF_PERSONS"><fmt:message key="tour.amount_of_persons"/>:</option>
        <option value="PRICE"><fmt:message key="tour.price"/></option>
        <option value="HOTEL_TYPE"><fmt:message key="tour.hotel_type"/></option>
        <option value="TOUR_TYPE"><fmt:message key="tour.tour_type"/></option>
    </select>
    <fmt:message key="tour.form.direction.header"/>:
    <select name="direction" id="direction">
        <option value="desc"><fmt:message key="tour.form.direction.desc"/></option>
        <option value="asc"><fmt:message key="tour.form.direction.asc"/></option>
    </select>
    <input type="submit" value="<fmt:message key="tour.form.direction.submit"/>">
</form>
<br>
<c:if test="${requestScope.get('isTourIdNotCorrect')==true}">
    <fmt:message key="tour.delete.message"/>
</c:if>
<c:if test="${sessionScope.get('currentOrderOfTours')!=null}">
    <fmt:message key="tour.current_order"/> <fmt:message
        key="tour.${sessionScope.get('currentOrderOfTours').name().toLowerCase()}"/><br>
    <fmt:message key="tour.current_direction"/> <fmt:message
        key="tour.form.direction.${sessionScope.get('currentDirection')}"/><br>
</c:if>
<table border="3" style="word-wrap: break-word;" width="500px">

    <c:forEach items="${requestScope.get('toursFromDb')}" var="tour">
        <c:if test="${tour.burning=='true'}">
            <tr>
                <td><fmt:message key="tour.burning"/></td>
                <td><fmt:message key="tour.burning.${tour.burning}"/></td>
            </tr>
        </c:if>
        <tr>
            <td><fmt:message key="tour.name"/></td>
            <c:if test="${language=='uk-ua'}">
                <td>${tour.name["uk_UA"]}</td>
            </c:if>
            <c:if test="${language=='en-gb'}">
                <td>${tour.name["en_GB"]}</td>
            </c:if>

        </
        #if>
        </tr>
        <tr>
            <td><fmt:message key="tour.description"/></td>
            <c:if test="${language=='uk-ua'}">
                <td style="width:200px;">${tour.description["uk_UA"]}</td>
            </c:if>
            <c:if test="${language=='en-gb'}">
                <td style="width:200px;">${tour.description["en_GB"]}</td>
            </c:if>
        </tr>
        <tr>
            <td><fmt:message key="tour.tour_type"/></td>
            <td>
                <c:forEach var="tourType" items="${tour.tourTypes}">
                    <fmt:message key="tour.tour_type.${tourType}"/><br>
                </c:forEach>
            </td>
        </tr>

        <tr>
            <td><fmt:message key="tour.price"/></td>
            <td>
                    ${tour.price} <fmt:message key="money.sign"/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="tour.hotel_type"/></td>
            <td>${tour.hotelType.amountOfStarsPretty}</td>
        </tr>
        <tr>
            <td><fmt:message key="tour.amount_of_persons"/></td>
            <td>${tour.amountOfPersons}</td>
        </tr>
        <tr>
            <td>
                <c:choose>
                <c:when test="${sessionScope.get('role') == 'ROLE_USER'}">
                    <form action="${pageContext.request.contextPath}/app/user/book-tour" method="post">
                        <input type="hidden" name="tour_id" value="${tour.id}"/>
                            <%--            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                        <input type="submit" value="<fmt:message key="tour.book_tour"/>">
                    </form>
                </c:when>
                <c:when test="${sessionScope.get('role') == 'ROLE_ADMIN'}">
                <form action="${pageContext.request.contextPath}/app/admin/edit-tour" method="get">
                    <input type="hidden" name="tour_id" value="${tour.id}"/>
                        <%--        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                    <input type="submit" value="<fmt:message key="tour.edit"/>">
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/app/admin/tour/delete" method="post">
                        <%--        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                    <input type="hidden" name="tour_id" value="${tour.id}"/>
                    <input type="submit" value="<fmt:message key="tour.delete"/>">
                </form>
            </td>

            </c:when>
            </c:choose>
        </tr>
    </c:forEach>
</table>
</body>
</html>

