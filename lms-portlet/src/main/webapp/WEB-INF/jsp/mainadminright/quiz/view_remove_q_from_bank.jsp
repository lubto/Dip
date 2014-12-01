<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@include file="../../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
 
<portlet:renderURL var="backUrl" />
<portlet:actionURL name="<%= ACTION_REMOVE_Q_FROM_BANK%>" var="removeUrl">
    <portlet:param name="<%= PARAM_ID%>" value="${questionPto.id}" />
    <portlet:param name="<%= PARAM_DELETE_CONFIRMED%>" value="true" />
    <portlet:param name="<%= PARAM_TYPE_Q%>" value="${typeQ}" />
    <portlet:param name="<%= ATTR_QUIZ_BANK_PTO%>" value="${quizBankPto}" />
</portlet:actionURL>

<div class="admin-right">  
    <div class="portlet-msg-alert application-question">
        <spring:message code="lms-msg-remove-question" arguments="${questionPto.name}" /> 
        <div class="buttons">
            <a tabindex="1" href="${removeUrl}" class="button"><spring:message code="common-btn-remove" /></a>
            <a tabindex="0" href="${backUrl}" class="button"><spring:message code="common-btn-cancel" /></a>
        </div>
    </div>
</div>