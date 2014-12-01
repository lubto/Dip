<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf" %>   
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %>

<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_FINISH_COURSE%>" /> 
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
     <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL> 

<div class="user-right"> 
 <span class="sideMape"><a href="${backUrl}"><spring:message code="side-map-your-finish-course" /></a> > <c:out value="${shortCutCourse}" /> > <spring:message code="side-map-test" /></span> 
    <h3><spring:message code="title-result" arguments="${shortCutCourse}" /></h3>
    <c:forEach items="${bankQPto}" var="item">
        <table>
            <h4><c:out value="${item.name}" /></h4>
            <p style="text-align: justify;"><c:out value="${item.description}" escapeXml="false"/></p><br/>
            <tr>
                <td>   
                    <a href="#" class="disabled"><img src="<%= request.getContextPath()%>/images/testIconOk.png"><c:out value="${item.name}" /></a>
                </td>
                <td>
                    <spring:message code="quiz-number-attempts" arguments="${item.attempts}" /><br/>
                        <spring:message code="quiz-highest-number" arguments="${item.points}" />
                </td> 
            </tr> <tr><td>&nbsp;</td></tr>
        </table>
    </c:forEach>
    <br/><br/>
    <button class="btnl" onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back" /></button>
</form>  
</div>


