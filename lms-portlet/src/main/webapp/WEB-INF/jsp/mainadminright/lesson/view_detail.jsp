<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.User"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="../../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>

<%
    ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
    String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:renderURL var="editUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_FORM_LESSON%>" />
    <portlet:param name="<%= PARAM_ID%>" value="${lessonPto.id}" />
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
</portlet:renderURL>
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_LESSON%>" /> 
    <portlet:param name="<%= PARAM_ID%>" value="${idCourse}" />
</portlet:renderURL>
<portlet:renderURL var="backToCourseUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />  
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" />
</portlet:renderURL>

<div class="admin-right">  
    <span class="sideMape"><a href="${backToCourseUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" /> > <a href="${backUrl}"><spring:message code="title-lesson" /></a> > <spring:message code="label-detail" /></span> 
    <h3><spring:message code="title-detail-lesson" /></h3>
    <table  style="width:100%">   
        <tr>
            <td width="15%" style="text-align: right;"><h5><spring:message code="name" /></h5></td>
            <td style="padding-left: 15px;" width="85%"><c:out value="${lessonPto.name}" /></td> 
        </tr> <!--<%--
        <tr> 
            <td><spring:message code="studyTime" /></td>
            <td><c:out value="${lessonPto.studyTime}" /></td> 
        </tr> --%>-->
        <tr> 
            <td style="text-align: right;"><h5><spring:message code="page" /></h5></td>
            <td style="padding-left: 15px;"><c:out value="${lessonPto.serialNumber}" /></td> 
        </tr>
        <tr> 
            <td style="text-align: right;"><h5><spring:message code="visible" /></h5></td> 
                    <c:choose>
                        <c:when test="${coursePto.visible == true}">
                    <td style="padding-left: 15px;"><spring:message code="no" /></td> 
                </c:when> 
                <c:otherwise>
                    <td style="padding-left: 15px;"><spring:message code="yes" /></td> 
                </c:otherwise> 
            </c:choose>    
        </tr> 
        <tr><td style="text-align: right; vertical-align: top;"><h5><spring:message code="content" /></h5></td>
            <td><br/><c:out value="${lessonPto.content}" escapeXml="false"/></td> 
        </tr> 
    </table> 
    <br/><br/>
    <div class="buttons">
        <button class="btnl" onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back" /></button>
        <button class="btnl" onclick="window.location = '${editUrl}';
                return false;"><spring:message code="common-btn-edit" /></button>
    </div>
</div>