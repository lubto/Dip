<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
        ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
        String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:renderURL var="htmlLinkUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ADD_ITEM_DOC%>" /> 
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
     <portlet:param name="<%= PARAM_ID_GROUP%>" value="${idGroup}" /> 
</portlet:renderURL>
<portlet:renderURL var="uploadUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_UPLOAD_ITEM_DOC%>" /> 
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
     <portlet:param name="<%= PARAM_ID_GROUP%>" value="${idGroup}" /> 
</portlet:renderURL>
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_GROUP_DOC%>" /> 
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>
<portlet:renderURL var="backToCourseUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" /> 
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<div class="admin-right"> 
    <span class="sideMape"><a href="${backToCourseUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" />  > <a href="${backUrl}"><spring:message code="mal-all-group-documents" /></a> > <spring:message code="label-add-doc" /></span> 
    <h3><spring:message code="title-create-item-doc" /></h3>
    <p><spring:message code="question-upload-or-insert" /></p>
 <br/> <br/>
 <a href="${backUrl}" class="btnl"><spring:message code="common-btn-back" /></a>&nbsp;
    <a href="${htmlLinkUrl}" class="btnl"><spring:message code="common-btn-insert-link" /></a>&nbsp;
     <a href="${uploadUrl}" class="btnl"><spring:message code="common-btn-upload-doc" /></a>
</div>