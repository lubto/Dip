<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@include file="../../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
 
<portlet:renderURL var="backUrl" />
<portlet:actionURL name="<%= ACTION_DELETE_Q_T_F %>" var="deleteUrl">
    <portlet:param name="<%= PARAM_ID %>" value="${questionPto.id}" />
    <portlet:param name="<%= PARAM_DELETE_CONFIRMED %>" value="true" />
    <portlet:param name="<%= PARAM_TYPE_Q%>" value="${typeQ}" />
</portlet:actionURL>

<div class="admin-right">  
    <div class="portlet-msg-alert application-question">
        <spring:message code="hello-msg-dele-question" arguments="${questionPto.name}" /> 
        <div class="buttons">
            <a tabindex="1" href="${deleteUrl}" class="button"><spring:message code="common-btn-delete" /></a>
            <a tabindex="0" href="${backUrl}" class="button"><spring:message code="common-btn-cancel" /></a>
        </div>
    </div>
</div>