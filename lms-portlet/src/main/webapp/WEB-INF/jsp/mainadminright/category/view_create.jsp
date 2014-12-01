<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@include file="../../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>

<portlet:actionURL name="<%= ACTION_CREATE_CATEGORY%>" var="actionUrl" />
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_CATEGORY%>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<div class="admin-right"> 
    <span class="sideMape"><a href="${backUrl}"><spring:message code="lable-category" /></a> > <spring:message code="common-btn-create" /></span>   
    <h3><spring:message code="title-create-category" /></h3><br/>
    <liferay-ui:error key="ErrorKey" message="common-msg-err-form-contains-errors" />
    <c:set var="heading" value="lms-msg-dummy-create" />
    <%@include file="components/form_category.jspf" %>
</div>