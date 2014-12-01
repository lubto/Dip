<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf" %>   
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %>
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" /> 
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
     <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL> 
<div class="user-right"> 

    <span class="sideMape"><a href="${backUrl}"><spring:message code="side-map-your-courses" /></a> > <c:out value="${shortCutCourse}" /> > <spring:message code="side-map-dms" /></span> 

    <h3><spring:message code="title-materials-for-study" /></h3> <br/>

    <c:forEach items="${groupDocPto}" var="itemH" >
    <h4><c:out value="${itemH.name}" /></h4>
    <p style="text-align: justify;"><c:out value="${itemH.description}" escapeXml="false"/></p><br/>
    <table id="tableDms">
        <c:set var="number" value="${1}" />
        <c:set var="size" value="${itemH.sizeItemDocument}" /> 

        <c:forEach items="${itemH.itemDocumentDto}" var="item" >
            <c:choose>
                <c:when test="${number%2 == 0}">
                    <td style="margin-left: 20px;padding-top: 10px;">
                        <a href="${item.link}" target="_blank"><img src="<%= request.getContextPath()%>${item.nameIcon}">&nbsp;<c:out value="${item.name}" /></a>
                    </td></tr>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${size!=number}">
                            <tr><td style="padding-top: 10px;">
                                    <a href="${item.link}" target="_blank"><img src="<%= request.getContextPath()%>${item.nameIcon}">&nbsp;<c:out value="${item.name}" /></a>
                                </td>
                            </c:when>
                            <c:when test="${size==number}">
                            <tr><td style="padding-top: 10px;">
                                    <a href="${item.link}" target="_blank"><img src="<%= request.getContextPath()%>${item.nameIcon}">&nbsp;<c:out value="${item.name}" /></a>
                                </td>
                                <td></td></tr>
                            </c:when>   
                        </c:choose>
                    </c:otherwise>
                </c:choose> 
                <c:set var="number" value="${number+1}" />
            </c:forEach>
    </table>   
    <hr>
    </c:forEach>

    <button class="btnl" onclick="window.location = '${backUrl}';
            return false;"><spring:message code="common-btn-back" /></button>
</div>