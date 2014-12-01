<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf" %> 
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %>  

<portlet:actionURL name="<%= ACTION_SEND_EMAIL%>" var="actionUrl" >
    <%--<portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> --%>
</portlet:actionURL>

<div class="user-right">  
    <liferay-ui:success key="KeySend" message="msg-email-send" />
    <liferay-ui:error key="ErrorKeySend" message="msg-error-email-send" /> 
    
    <h3><spring:message code="title-question-to-lector"/></h3> 
    <c:set var="heading" value="lms-msg-dummy-create" />
    <%@include file="components/form_email.jspf" %>
</div>
