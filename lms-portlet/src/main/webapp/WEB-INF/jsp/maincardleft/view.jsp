<%@ page pageEncoding="UTF-8" %>
<%@include file="../init.jspf" %> 
<%@page import="static eu.lms.portlet.userleft.MainCardLeftPortletConstants.*" %>

<portlet:actionURL name="<%= ACTION_SEND%>" var="forStudyUrl">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_COURSE_FOR_STUDY%>" />
</portlet:actionURL>
<portlet:actionURL name="<%= ACTION_SEND%>" var="finishCourseUrl">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_FINISH_COURSE%>" />
</portlet:actionURL>
<portlet:actionURL name="<%= ACTION_SEND%>" var="questionUrl">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_QUESTION%>" />
</portlet:actionURL>
   <%-- <portlet:actionURL name="<%= ACTION_SEND%>" var="profilUrl">
        <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_PROFIL%>" />
    </portlet:actionURL>--%>
<portlet:actionURL name="<%= ACTION_SEND%>" var="searchCourseUrl">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_SEACHR_COURSE%>" />
</portlet:actionURL>

<div class="user-left"> 
    <a href="${forStudyUrl}"><spring:message code="main-left-course-to-study" /></a><br/>
    <a href="${finishCourseUrl}"><spring:message code="main-left-finish-course" /></a><br/>
    <a href="${questionUrl}"><spring:message code="main-left-question-to-lector" /></a><br/>
    <%--<a href="${profilUrl}"><spring:message code="main-left-my-profil" /></a><br/>--%>
    <a href="${searchCourseUrl}"><spring:message code="main-left-search-course" /></a>
</div>