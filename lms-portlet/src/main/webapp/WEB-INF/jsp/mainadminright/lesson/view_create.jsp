<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>

<%
        ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
        String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:actionURL name="<%= ACTION_CREATE_LESSON%>" var="actionUrl" >
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> 
</portlet:actionURL>
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_LESSON%>" /> 
    <portlet:param name="<%= PARAM_ID%>" value="${idCourse}" />
</portlet:renderURL>
<portlet:renderURL var="backToCourseUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />  
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
</portlet:renderURL>

<div class="admin-right"> 
    <span class="sideMape"><a href="${backToCourseUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" /> > <a href="${backUrl}" ><spring:message code="title-lesson" /></a> > <spring:message code="common-btn-create" /></span> 
    <h3><spring:message code="title-create-lesson" /></h3>
    <c:set var="heading" value="lms-msg-dummy-create" />
    <%@include file="components/form_lesson.jspf" %>
</div>
