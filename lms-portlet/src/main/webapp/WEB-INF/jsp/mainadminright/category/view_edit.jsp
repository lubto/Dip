<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@include file="../../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>

 <portlet:actionURL name="<%= ACTION_SAVE_CATEGORY %>" var="actionUrl" />
<portlet:renderURL var="backUrl">
    <portlet:param name="<%= PARAM_PAGE %>" value="<%= PAGE_DETAIL_CATEGORY %>" />
    <portlet:param name="<%= PARAM_ID %>" value="${categoryPto.id}" />
</portlet:renderURL>
<portlet:renderURL var="categoryUrl">
    <portlet:param name="<%= PARAM_PAGE %>" value="<%= PAGE_ALL_CATEGORY %>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<div class="admin-right"> 
    <liferay-ui:error key="ErrorKey" message="common-msg-err-form-contains-errors" />
   
    <span class="sideMape"><a href="${categoryUrl}"><spring:message code="lable-category" /></a> ><a href="${backUrl}"> <spring:message code="label-detail" /></a>> <spring:message code="common-btn-edit" /></span> 
    <h3><spring:message code="title-edit-category" /></h3>
    <c:set var="heading" value="lms-msg-dummy-update" />
    <%@include file="components/form_category.jspf" %>
</div>