<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>   
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %> 

<portlet:renderURL var="backUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_CATEGORY%>" />  
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
</portlet:renderURL>   

<div class="user-right"> 
    <span class="sideMape"><spring:message code="side-map-free-courses" /> > <a href="${backUrl}"><spring:message code="side-map-category"/></a> > <spring:message code="side-map-course"/> </span> 

    <h3><spring:message code="title-free-course-chose" /></h3> <br/>
    <p style="text-align: justify;"><spring:message code="title-no-category" /></p><br/><br/>

    <button class="btnl" onclick="window.location = '${backUrl}';"><spring:message code="back" /></button> 
</div>