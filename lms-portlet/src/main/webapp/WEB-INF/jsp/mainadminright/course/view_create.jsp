<%@page contentType="text/html" pageEncoding="windows-1250"%> 
<%@include file="../../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>

<%
    String userId = request.getRemoteUser();
%>
<portlet:actionURL name="<%= ACTION_CREATE_COURSE %>" var="actionUrl" /> 
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
    <portlet:param name="<%= PARAM_ID_USER %>" value="<%= userId%>" />
</portlet:renderURL>

<div class="admin-right">  
    <span class="sideMape"><a href="${backUrl}"><spring:message code="label-course" /></a> > <spring:message code="common-btn-create" /></span> 
      <h3><spring:message code="title-create-course" /></h3>
    <liferay-ui:error key="ErrorKey" message="common-msg-err-form-contains-errors" />
    <c:set var="heading" value="lms-msg-dummy-create" />
    <%@include file="components/form_course.jspf" %>
</div>
