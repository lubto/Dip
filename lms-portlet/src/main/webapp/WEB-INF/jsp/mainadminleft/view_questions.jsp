<%@ page pageEncoding="UTF-8" %>
<%@include file="../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainleft.MainLeftPortletConstants.*" %>

<portlet:actionURL name="<%= ACTION_SEND%>" var="urlAllCategory">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_ALL_CATEGORY%>" />
    <portlet:param name="<%= ATTR%>" value="k" />
</portlet:actionURL>
<portlet:actionURL name="<%= ACTION_SEND%>" var="urlCreateCategory">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_CREATE_CATEGORY%>" />
    <portlet:param name="<%= ATTR%>" value="k" />
</portlet:actionURL>
<portlet:actionURL name="<%= ACTION_SEND%>" var="urlAllCourse">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_ALL_COURSE%>" />
    <portlet:param name="<%= ATTR%>" value="c" />
</portlet:actionURL>
<portlet:actionURL name="<%= ACTION_SEND%>" var="urlCreateCourse">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_CREATE_COURSE%>" />
    <portlet:param name="<%= ATTR%>" value="c" />
</portlet:actionURL>
<portlet:actionURL name="<%= ACTION_SEND%>" var="urlSetGroup">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_SETUP_GROUP%>" />
    <portlet:param name="<%= ATTR%>" value="a" />
</portlet:actionURL>
<portlet:actionURL name="<%= ACTION_SEND%>" var="urlAllUsers">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_ALL_USERS%>" />
    <portlet:param name="<%= ATTR%>" value="a" />
</portlet:actionURL>

<portlet:actionURL name="<%= ACTION_SEND%>" var="urlCreateQ">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_CREATE_Q%>" /> 
    <portlet:param name="<%= ATTR%>" value="q" />
    <%-- <portlet:param name="<%= PARAM%>" value="" /> --%>
</portlet:actionURL>
<portlet:actionURL name="<%= ACTION_SEND%>" var="urlAllQ">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_ALL_Q%>" />
    <portlet:param name="<%= ATTR%>" value="q" />
</portlet:actionURL> 
<portlet:actionURL name="<%= ACTION_SEND%>" var="urlAllQbank">
    <portlet:param name="<%= PARAM_ID%>" value="<%= SEND_ALL_Q_BANK%>" />
    <portlet:param name="<%= ATTR%>" value="q" />
</portlet:actionURL>

<div class="admin-left">  
    <spring:message code="mal-category" var="var1"/>
    <spring:message code="mal-course" var="var2"/>
    <spring:message code="mal-quiz" var="var3" /> 
    <spring:message code="mal-users" var="var4" /> 
    <br/> <!--extended="true" v panel container a v panel extended="true" -->
    <liferay-ui:panel-container accordion="false" >  
        <liferay-ui:panel title="${var1}" defaultState="true">
            <a href="${urlAllCategory}"><spring:message code="mal-all-category" /></a><br/>
            <% if (request.isUserInRole("LMS-administrator")) {
            %>
            <a href="${urlCreateCategory}"><spring:message code="mal-create-category" /></a> 
            <%}%>
        </liferay-ui:panel>  
        <liferay-ui:panel title="${var2}" defaultState="false">  
            <a href="${urlAllCourse}"><spring:message code="mal-all-course" /></a><br/>
            <a href="${urlCreateCourse}"><spring:message code="mal-create-course" /></a>  
        </liferay-ui:panel>  
        <liferay-ui:panel title="${var3}"> 
            <a href="${urlCreateQ}"><spring:message code="mal-create-question" /></a><br/>
            <a href="${urlAllQ}"><spring:message code="mal-all-questions" /></a><br/> 
            <a href="${urlAllQbank}"><spring:message code="mal-all-q-bank" /></a><br/>  
        </liferay-ui:panel>
        <% if (request.isUserInRole("LMS-administrator")) {
        %>
        <liferay-ui:panel title="${var4}" defaultState="false">   
            <a href="${urlAllUsers}"><spring:message code="mal-all-users" /></a><br/> 
            <a href="${urlSetGroup}"><spring:message code="mal-set-group" /></a> 
        </liferay-ui:panel> 
        <%}%>
    </liferay-ui:panel-container>  
</div>