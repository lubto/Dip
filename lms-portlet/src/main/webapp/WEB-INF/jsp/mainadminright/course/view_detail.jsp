<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@include file="../../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
    String userId = request.getRemoteUser();
%>
<portlet:renderURL var="editUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_FORM_COURSE%>" />
    <portlet:param name="<%= PARAM_ID%>" value="${coursePto.id}" />
</portlet:renderURL>
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
    <portlet:param name="<%= PARAM_ID_USER %>" value="<%= userId%>" />
</portlet:renderURL>

<div class="admin-right">  
     <span class="sideMape"><a href="${backUrl}"><spring:message code="label-course" /></a> > <spring:message code="label-detail" /></span> 
<h3><spring:message code="title-detail-course" /></h3>
    <table style="width:100%">   
        <tr>
            <td width="20%" style="text-align: right";><h5><spring:message code="name" /></h5></td>
            <td style="padding-left: 15px;" width="80%"><c:out value="${coursePto.name}" /></td> 
        </tr> 
        <tr> 
            <td  style="text-align: right"><h5><spring:message code="shortName" /></h5></td>
            <td style="padding-left: 15px;"><c:out value="${coursePto.shortName}" /></td> 
        </tr>
        <tr> 
            <td style="text-align: right"><h5><spring:message code="description" /></h5></td>
            <td style="padding-left: 15px;"><c:out value="${coursePto.description}" /></td> 
        </tr>
        <tr> 
            <td style="text-align: right"><h5><spring:message code="category" /></h5></td>
            <td style="padding-left: 15px;"><c:out value="${coursePto.category.name}" /></td> 
        </tr> 
        <tr> 
            <td style="text-align: right"><h5><spring:message code="visible" /></h5></td> 
            <c:choose>
                <c:when test="${coursePto.visible == true}">
                    <td style="padding-left: 15px;"><spring:message code="yes" /></td> 
                </c:when> 
                <c:otherwise>
                    <td style="padding-left: 15px;"><spring:message code="no" /></td> 
                </c:otherwise> 
            </c:choose>    
        </tr>
        <tr> 
            <td style="text-align: right"><h5><spring:message code="dateOfCreation" /></h5></td>
            <td style="padding-left: 15px;"><c:out value="${coursePto.dateOfCreation}" /></td> 
        </tr> 
    </table> 
        <br/><br/>
    <div class="buttons">
        <button class="btnl"  onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back" /></button>
        <button class="btnl"  onclick="window.location = '${editUrl}';
                return false;"><spring:message code="common-btn-edit" /></button>
        
    </div>
</div>