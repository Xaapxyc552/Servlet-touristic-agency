<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:set scope="page" var="language" value="${cookie.get('language').value}"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="messages"/>

<jsp:include page="../common/common.jsp"/>

<c:if test="${requestScope.get('isTourCreated')==true}">
    <fmt:message key="tour.created"/>
</c:if>

<form action="${pageContext.request.contextPath}/app/admin/new-tour/create" method="post">
<%--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
    <table>
        <tr>
            <td>
                <label for="name_uk_UA"><fmt:message key="tour.form.name.ukrainian"/></label><br>
                <input type="text" id="name_uk_UA" name="name_uk_UA">
            </td>
            <td>
                <label for="name_en_GB"><fmt:message key="tour.form.name.english"/></label><br>
                <input type="text" id="name_en_GB" name="name_en_GB">
            </td>
        </tr>
        <tr>
            <td>
                <label for="description_uk_UA"><fmt:message key="tour.form.description.ukrainian"/></label><br>
                <textarea name="description_uk_UA" id="description_uk_UA" cols="30" rows="10"></textarea>
            </td>
            <td>
                <label for="description_en_GB"><fmt:message key="tour.form.description.english"/></label><br>
                <textarea name="description_en_GB" id="description_en_GB" cols="30" rows="10"></textarea>
            </td>
        </tr>
        <tr>
            <td>
                <label for="amountOfPersons"><fmt:message key="tour.form.amount_of_person"/></label><br>
                <input type="number" id="amountOfPersons" name="amountOfPersons">
            </td>
        </tr>
        <tr>
            <td>
                <label for="price"><fmt:message key="tour.form.price"/></label><br>
                <input type="number" id="price" name="price">
            </td>
        </tr>
        <tr>
            <td>
                <label for="tourTypes"><fmt:message key="tour.form.tour_type.header"/></label><br>
                <select multiple="multiple" name="tourTypes" id="tourTypes">
                    <option value="SHOPPING"><fmt:message key="tour.form.tour_type.shopping"/></option>
                    <option value="RECREATION"><fmt:message key="tour.form.tour_type.recreation"/></option>
                    <option value="EXCURSION"><fmt:message key="tour.form.tour_type.excursion"/></option>
                </select>
            </td>
        </tr>
        <tr>
            <td><label for="hotelType"><fmt:message key="tour.form.hotel_type.header"/></label><br>
                <select name="hotelType" id="hotelType">
                    <option value="ONE_STAR"><fmt:message key="tour.form.hotel_type.one_star"/></option>
                    <option value="TWO_STAR"><fmt:message key="tour.form.hotel_type.two_star"/></option>
                    <option value="THREE_STAR"><fmt:message key="tour.form.hotel_type.three_star"/></option>
                    <option value="FOUR_STAR"><fmt:message key="tour.form.hotel_type.four_star"/></option>
                    <option value="FIVE_STAR"><fmt:message key="tour.form.hotel_type.five_star"/></option>
                </select>
            </td>
        </tr>
        <tr>
            <td><input type="submit">
            </td>
        </tr>
    </table>
</form>
