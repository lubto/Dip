<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@include file="../../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
 <%
    String userId = request.getRemoteUser();
%>
<portlet:renderURL var="editUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_FORM_CATEGORY%>" />
    <portlet:param name="<%= PARAM_ID%>" value="${categoryPto.id}" />
</portlet:renderURL>
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
    <portlet:param name="<%= PARAM_ID_USER %>" value="<%= userId%>" />
</portlet:renderURL>

<div class="admin-right"> 
<span class="sideMape"><a href="${backUrl}"><spring:message code="label-course" /></a> > <spring:message code="lable-category" /> > <spring:message code="label-detail" /></span>
   
    <h3><spring:message code="title-detail-category" /></h3>
    <table style="width:100%;">   
        <tr>
            <td width="20%" style="text-align: right;"><h5><spring:message code="name" /></h5></td>
            <td style="padding-left: 15px;" width="80%"><c:out value="${categoryPto.name}" /></td> 
        </tr> 
        <tr> 
            <td style="text-align: right; vertical-align: top;"><h5><spring:message code="description" /></h5></td>
            <td style="padding-left: 15px;"><c:out value="${categoryPto.description}" /></td> 
        </tr>
        <tr> 
            <td style="text-align: right;"><h5><spring:message code="visible" /></h5></td>
            <td style="padding-left: 15px;"> 
                <c:choose>
                    <c:when test="${categoryPto.visible == true}">
                        <spring:message code="yes" htmlEscape="true" />
                    </c:when>
                    <c:otherwise>
                        <spring:message code="no" htmlEscape="true" />
                    </c:otherwise> 
                </c:choose>
            </td> 
        </tr>
    </table>
    <br/><br/>
    <div class="buttons">
        <button class="btnl" onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back" /></button> 
        <% if (request.isUserInRole("LMS-administrator")) {
        %>
        <button class="btnl" onclick="window.location = '${editUrl}';
                return false;"><spring:message code="common-btn-edit" /></button>
        <%}%>
    </div>
</div>