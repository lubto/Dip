<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>

<portlet:actionURL name="<%= ACTION_CREATE_Q_MCH%>" var="actionUrl" />

<div class="admin-right">  
    <span class="sideMape"><spring:message code="side-map-queston" /> > <spring:message code="side-map-create" /></span>  

    <liferay-ui:error key="ErrorKey" message="common-msg-err-form-contains-errors" />
    <liferay-ui:success key="SuccessKey" message="lms-msg-dummy-created" />

    <h3><spring:message code="mal-create-question" /></h3>
    <c:set var="heading" value="lms-msg-dummy-create" />
    <%@include file="component/multichoice.jspf" %> 
</div>