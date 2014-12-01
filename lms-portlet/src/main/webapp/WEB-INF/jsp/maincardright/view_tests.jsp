<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf" %>   
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %>

<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" /> 
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" /> 
</portlet:renderURL> 
<div class="user-right"> 

    <span class="sideMape"><a href="${backUrl}"><spring:message code="side-map-your-courses" /></a> > <c:out value="${shortCutCourse}" /> > <spring:message code="side-map-test" /></span> 

    <h3><spring:message code="title-tests" /></h3> <br/>

    <c:forEach items="${bankQPto}" var="item">
        <table>
            <h4><c:out value="${item.name}" /></h4>
            <p style="text-align: justify;"><c:out value="${item.description}" escapeXml="false"/></p><br/>
            <tr>
                <td> 
                    <portlet:renderURL var="actionUrl" >
                        <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_TEST_Q%>" /> 
                        <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
                        <portlet:param name="<%= PARAM_ID_BANK%>" value="${item.id}" />
                        <portlet:param name="<%= PARAM_ID%>" value="${paramId}" />
                        <portlet:param name="<%= ATTR_ID_COURSE_ITEM%>" value="${courseItem}" />
                        <portlet:param name="<%= ATTR_USER_BANKQ%>" value="${item.idUserBankQ}" />
                    </portlet:renderURL> 
                    <c:choose> 
                        <c:when test="${item.completed == true}">
                            <a href="#" class="disabled"><img src="<%= request.getContextPath()%>${item.nameIcon}"><c:out value="${item.name}" /></a>
                            </c:when>
                            <c:otherwise>
                            <a href="${actionUrl}"><img src="<%= request.getContextPath()%>${item.nameIcon}"><c:out value="${item.name}" /></a>
                            </c:otherwise>
                        </c:choose> 
                </td> 
            </tr> <tr><td>&nbsp;</td></tr>
        </table>
    </c:forEach>
    <br/><br/>
    <button class="btnl" onclick="window.location = '${backUrl}';
            return false;"><spring:message code="common-btn-back" /></button>
</div>