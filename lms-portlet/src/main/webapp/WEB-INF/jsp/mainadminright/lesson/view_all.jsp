<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%--
<portlet:renderURL var="editUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_FORM_CATEGORY%>" />
    <portlet:param name="<%= PARAM_ID%>" value="${categoryPto.id}" /> 
</portlet:renderURL>--%>
<portlet:renderURL var="backUrl" />

<div class="admin-right">  
    <table> <c:forEach items="${lessonPto}" var="lessonPto">   
        <tr>
            <td><spring:message code="name" /></td>
            <td><c:out value="${lessonPto.name}" /></td> 
        </tr> 
        <tr>
            <td><spring:message code="serialNumber" /></td>
            <td><c:out value="${lessonPto.serialNumber}" /></td> 
        </tr> 
        <tr> 
            <td><spring:message code="description" /></td>
            <td><c:out value="${lessonPto.content}" /></td> 
        </tr></br></br>
         </c:forEach>
    </table> 
    <div class="buttons">
        <button class="btnl"  onclick="window.location = '${editUrl}';
                return false;"><spring:message code="common-btn-edit" /></button>
        <button class="btnl"  onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back" /></button>
    </div>
</div>