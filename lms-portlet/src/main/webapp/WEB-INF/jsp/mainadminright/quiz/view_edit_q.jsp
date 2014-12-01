<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
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
<portlet:actionURL name="<%= ACTION_SAVE_Q%>" var="actionUrl" >
    <portlet:param name="<%= PARAM_ID%>" value="${entityId}" /> 
</portlet:actionURL>

<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_Q%>" />
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<div class="admin-right">  
    <span class="sideMape"><a href="${backUrl}"><spring:message code="side-map-queston" /></a> > <spring:message code="side-map-edit" /></span>  
  
    <liferay-ui:error key="ErrorKey" message="common-msg-err-form-contains-errors" />
    <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" />
 
    <h3><spring:message code="mal-edit-question" /></h3>
    <c:set var="heading" value="lms-msg-dummy-create" />
    <%@include file="component/form_multiplechoice.jspf" %> 
</div>