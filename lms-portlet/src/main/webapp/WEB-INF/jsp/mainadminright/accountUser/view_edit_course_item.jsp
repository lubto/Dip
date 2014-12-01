<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %> 

<portlet:actionURL name="<%= ACTION_SAVE_COURSE_ITEM_I %>" var="actionUrl">
    <portlet:param name="<%= PARAM_ID_ACCOUNT %>" value="${idAccount}" /> 
</portlet:actionURL>
<portlet:renderURL var="backUrl"> 
    <portlet:param name="<%= PARAM_PAGE %>" value="<%= PAGE_ITEMS_USER %>" />
    <portlet:param name="<%= PARAM_ID %>" value="${idAccount}" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>
<portlet:renderURL var="accountUrl">
    <portlet:param name="<%= PARAM_PAGE %>" value="<%= PAGE_ALL_USERS %>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<div class="admin-right">
    <span class="sideMape"><a href="${accountUrl}"><spring:message code="label-account" /></a> > <a href="${backUrl}" ><c:out value="${shortcutName}" /></a> > <spring:message code="common-btn-edit" /></span> 
 
    <liferay-ui:error key="ErrorKey" message="common-msg-err-form-contains-errors" />
    <h3><spring:message code="title-edit-acount" /></h3>
    <c:set var="heading" value="lms-msg-dummy-update" />
    <%@include file="../course/users/components/form_course_item.jspf" %>
</div>