<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@include file="../../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>

<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<portlet:actionURL name="<%= ACTION_DELETE_COURSE %>" var="deleteUrl">
    <portlet:param name="<%= PARAM_ID %>" value="${coursePto.id}" />
    <portlet:param name="<%= PARAM_DELETE_CONFIRMED %>" value="true" />
</portlet:actionURL>

<div class="admin-right">  
    <div class="portlet-msg-alert application-question">
        <spring:message code="hello-msg-dele-question" arguments="${accountUserPto.userId}" /> 
        <div class="buttons">
            <a tabindex="1" href="${deleteUrl}" class="button"><spring:message code="common-btn-delete" /></a>
            <a tabindex="0" href="${backUrl}" class="button"><spring:message code="common-btn-cancel" /></a>
        </div>
    </div>
</div>