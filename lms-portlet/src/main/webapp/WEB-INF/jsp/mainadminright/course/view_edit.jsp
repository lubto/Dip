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
 <portlet:actionURL name="<%= ACTION_SAVE_COURSE %>" var="actionUrl" />
<portlet:renderURL var="backUrl">
    <portlet:param name="<%= PARAM_PAGE %>" value="<%= PAGE_DETAIL_COURSE %>" />
    <portlet:param name="<%= PARAM_ID %>" value="${coursePto.id}" /> 
</portlet:renderURL>
<portlet:renderURL var="courseUrl">
    <portlet:param name="<%= PARAM_PAGE %>" value="<%= PAGE_ALL_COURSE %>" /> 
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " /> 
</portlet:renderURL>

<div class="admin-right">
    <span class="sideMape"><a href="${courseUrl}"><spring:message code="label-course" /></a> ><a href="${backUrl}"> <spring:message code="label-detail" /></a>> <spring:message code="common-btn-edit" /></span> 
    <liferay-ui:error key="ErrorKey" message="common-msg-err-form-contains-errors" />
    <h3><spring:message code="title-edit-course" /></h3>
    <c:set var="heading" value="lms-msg-dummy-update" />
    <%@include file="components/form_course.jspf" %>
</div>