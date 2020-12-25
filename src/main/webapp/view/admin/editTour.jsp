<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false" %>

<c:set scope="page" var="language" value="${cookie.get('language').value}"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="messages"/>

<jsp:include page="../common/common.jsp"/>

<c:if test="${requestScope.get('isTourCreated')==true}">
    <fmt:message key="tour.created"/>
</c:if>

<form action="${pageContext.request.contextPath}/app/admin/edit-tour/update" method="post" name="tourDTO" id="tourDTO">
    <%--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
    <c:set var="tourToEdit" value="${requestScope.get('tourToEdit')}"/>

    <input type="hidden" name="tour_id" value="${tourToEdit.id}"/>
    <table>
        <tr>
            <td>
                <fmt:message key="tour.form.burning"/>
                <select name="burning" id="burning">
                    <option value="true"
                            <c:if test="${tourToEdit.burning=='true'}">
                                selected="selected"
                            </c:if>>
                        <fmt:message key="tour.form.burning.yes"/>
                    </option>
                    <option value="false"
                            <c:if test="${tourToEdit.burning=='false'}">
                                selected="selected"
                            </c:if>>
                        <fmt:message key="tour.form.burning.no"/>
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="name_uk_UA"><fmt:message key="tour.form.name.ukrainian"/></label><br>
                <input type="text" id="name_uk_UA" name="name_uk_UA" value="${tourToEdit.name.get('uk_UA')}">
            </td>
            <td>
                <label for="name_en_GB"><fmt:message key="tour.form.name.english"/></label><br>
                <input type="text" id="name_en_GB" name="name_en_GB" value="${tourToEdit.name.get('en_GB')}">
            </td>
        </tr>
        <tr>
            <td>
                <label for="description_uk_UA"><fmt:message key="tour.form.description.ukrainian"/></label><br>
                <textarea name="description_uk_UA" id="description_uk_UA" cols="30"
                          rows="10">${tourToEdit.description.get('uk_UA')}
                </textarea>
            </td>
            <td>
                <label for="description_en_GB"><fmt:message key="tour.form.description.english"/></label><br>
                <textarea name="description_en_GB" id="description_en_GB" cols="30"
                          rows="10">${tourToEdit.description.get('en_GB')}
                </textarea>
            </td>
        </tr>
        <tr>
            <td>
                <label for="amountOfPersons"><fmt:message key="tour.form.amount_of_person"/></label><br>
                <input type="number" id="amountOfPersons" name="amountOfPersons" value="${tourToEdit.amountOfPersons}">
            </td>
        </tr>
        <tr>
            <td>
                <label for="price"><fmt:message key="tour.form.price"/></label><br>
                <input type="number" id="price" name="price" value="${Double.valueOf(tourToEdit.getPrice())}">
            </td>
        </tr>
        <tr>
            <td>
                <c:set var="string3" value="${fn:join(tourToEdit.getTourTypes().toArray(), ',')}"/>
                <label for="tourTypes"><fmt:message key="tour.form.tour_type.header"/></label><br>
                <select multiple="multiple" name="tourTypes" id="tourTypes">
                    <option value="SHOPPING"
                            <c:if test="${fn:contains(string3, 'SHOPPING')}">
                                selected="selected"
                            </c:if>>
                        <fmt:message key="tour.form.tour_type.shopping"/></option>
                    <option value="RECREATION"
                            <c:if test="${fn:contains(string3, 'RECREATION')}">
                                selected="selected"
                            </c:if>>
                        <fmt:message key="tour.form.tour_type.recreation"/></option>
                    <option value="EXCURSION"
                            <c:if test="${fn:contains(string3, 'EXCURSION')}">
                                selected="selected"
                            </c:if>>
                        <fmt:message key="tour.form.tour_type.excursion"/></option>
                </select>
            </td>
        </tr>
        <tr>
            <td><label for="hotelType"><fmt:message key="tour.form.hotel_type.header"/></label><br>
                <select name="hotelType" id="hotelType">
                    <option value="ONE_STAR" <c:if
                            test="${tourToEdit.hotelType=='ONE_STAR'}"> selected="selected"</c:if>>
                        <fmt:message key="tour.form.hotel_type.one_star"/>
                    </option>
                    <option value="TWO_STAR" <c:if
                            test="${tourToEdit.hotelType=='TWO_STAR'}"> selected="selected"</c:if>>
                        <fmt:message key="tour.form.hotel_type.two_star"/>
                    </option>
                    <option value="THREE_STAR" <c:if
                            test="${tourToEdit.hotelType=='THREE_STAR'}"> selected="selected"</c:if>>
                        <fmt:message key="tour.form.hotel_type.three_star"/>
                    </option>
                    <option value="FOUR_STAR" <c:if
                            test="${tourToEdit.hotelType=='FOUR_STAR'}"> selected="selected"</c:if>>
                        <fmt:message key="tour.form.hotel_type.four_star"/>
                    </option>
                    <option value="FIVE_STAR" <c:if
                            test="${tourToEdit.hotelType=='FIVE_STAR'}"> selected="selected"</c:if>>
                        <fmt:message key="tour.form.hotel_type.five_star"/>
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td><input type="submit">
            </td>
        </tr>
    </table>
</form>
