<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>   
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %>
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" /> 
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
     <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL> 
<div class="user-right"> 

 <span class="sideMape"><a href="${backUrl}"><spring:message code="side-map-your-courses" /></a> > <c:out value="${shortCutCourse}" /> > <spring:message code="side-map-view" /></span> 
  <h3><spring:message code="mal-btn-show-lesson"/></h3> <br/>
  
    <p style="text-align: justify;"><spring:message code="title-no-lesson" /></p><br/>
     
    <button class="btnl" onclick="window.location = '${backUrl}';
            return false;"><spring:message code="common-btn-back" /></button>
</div>