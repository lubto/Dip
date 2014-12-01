<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>

<portlet:actionURL name="<%= ACTION_CREATE_Q_MCH%>" var="actionUrl" />

<div class="admin-right"> 

    <span class="sideMape"><spring:message code="side-map-queston" /> > <spring:message code="side-map-create" /></span>  
    <h3><spring:message code="mal-edit-question" /></h3> 
    <%@include file="tab_component/form_multiplechoice.jspf" %>  
</div>