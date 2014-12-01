<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@include file="../../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
    ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
    String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:renderURL var="backUrl" />
<portlet:actionURL name="<%= ACTION_DELETE_Q_BANK%>" var="deleteUrl">
    <portlet:param name="<%= PARAM_ID%>" value="${quizBankPto.id}" />
    <portlet:param name="<%= PARAM_DELETE_CONFIRMED%>" value="true" /> 
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 

</portlet:actionURL>

<div class="admin-right">  
    <div class="portlet-msg-alert application-question">
        <spring:message code="hello-msg-dele-question" arguments="${quizBankPto.name}" /> 
        <div class="buttons">
            <a tabindex="1" href="${deleteUrl}" class="button"><spring:message code="common-btn-delete" /></a>
            <a tabindex="0" href="${backUrl}" class="button"><spring:message code="common-btn-cancel" /></a>
        </div>
    </div>
</div>