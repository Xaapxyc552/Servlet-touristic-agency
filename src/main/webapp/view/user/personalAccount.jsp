<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie.get('language').value}" scope="session"/>
<fmt:setBundle basename="messages"/>

<#import "spring.ftl" as spring/>
<#import "macroses/commonMacro.ftlh" as commonMacro>
<@commonMacro.headerMacro/>

<@spring.message "personal_page.firstname"/>${firstname}<br>
<@spring.message "personal_page.email"/>${email}<br>
<@spring.message "personal_page.username"/>${username}<br>
<@spring.message "personal_page.role"/>${userRole}<br>
<@spring.message "personal_page.money"/>
<@commonMacro.showMoneyByLocale money "${requestContext.locale}" dollarCourse/><br>
<br>
<form action="/user/recharge" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <@spring.message "personal_page.recharge"/><br>
    <label for="amountOfCharge"><input type="number" id="amountOfCharge" name="amountOfCharge"></label>
    <input type="submit" value="<@spring.message "personal_page.recharge"/>">
</form>


<@commonMacro.pagingMacro pagesSequence=pagesSequence currentPage=currentPage  href="/user/personal-account/"/>

<table>
    <@spring.message "check.header"/> <br>
    <#list userChecks as check>
    <tr>
        <th><@spring.message "check.tour_name"/></th>
        <#if "${requestContext.locale}"=="uk_UA">
        <td>${check.tour.name["uk_UA"]}</td>
        <#else>
        <td>${check.tour.name["en_GB"]}</td>
    </#if>
    </tr>
    <tr>
        <th><@spring.message "check.total_price"/></th>
        <th>
            <@commonMacro.showMoneyByLocale check.totalPrice "${requestContext.locale}" dollarCourse/>
        </th>
    </tr>
    <tr>
        <th><@spring.message "check.status"/></th>
        <th><@spring.message "check.status.${check.status.status}"/></th>
    </tr>
    <#if check.status.status.name()="WAITING_FOR_CONFIRM">
    <tr>
        <th>
            <form action="/tours/remove/${check.id}" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" value="<@spring.message "check.remove"/>">

            </form>
        </th>
    </tr>
</#if>
<tr>
    <th><br></th>
</tr>
<#else> <@spring.message "check.you_dont_have_bookings_yet"/>
</#list>
</table>
<#--//счет,имя, роль, ссылки на другие места-->