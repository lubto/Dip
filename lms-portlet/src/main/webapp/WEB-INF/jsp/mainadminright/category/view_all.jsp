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
          <table> <c:forEach items="${categoryPto}" var="categoryPto">   
        <tr>
            <td><spring:message code="categoryPto.name" /></td>
            <td><c:out value="${categoryPto.name}" /></td> 
        </tr> 
        <tr> 
            <td><spring:message code="description" /></td>
            <td><c:out value="${categoryPto.description}" /></td> 
        </tr></br></br>
         </c:forEach>
    </table>
     
    <div class="buttons">
        <button onclick="window.location = '${editUrl}';
                return false;"><spring:message code="common-btn-edit" /></button>
        <button onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back" /></button>
    </div>
</div>