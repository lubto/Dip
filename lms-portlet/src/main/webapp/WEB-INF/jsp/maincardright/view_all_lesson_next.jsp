<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf" %> 
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %> 

<portlet:renderURL var="nextUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_LESSON_NEXT%>" />
    <portlet:param name="<%= PARAM_ID%>" value="${(lessonPto.serialNumber)+1}" /> 
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />   
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
</portlet:renderURL> 
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_LESSON_NEXT%>" />
    <portlet:param name="<%= PARAM_ID%>" value="${(lessonPto.serialNumber)-1}" />
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />   
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
</portlet:renderURL>
<portlet:renderURL var="cancelUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />  
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
     <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL> 

<div class="user-right">  
    <span class="sideMape"><a href="${cancelUrl}"><spring:message code="side-map-your-courses" /></a> > <c:out value="${shortCutCourse}" /> > <spring:message code="side-map-view" /></span> 
    <h3><spring:message code="mal-btn-show-lesson"/></h3>
    <table width="100%" >   
        <tr>  
            <td width="5%"></td>
            <td><c:out value="${lessonPto.content}" escapeXml="false"/></td> 
        </tr> 
         <tr>  
             <td></td> 
             <td style="text-align: right;"><spring:message code="page" /> <c:out value="${lessonPto.serialNumber}" /></td> 
        </tr>
    </table>  
<br/><br/>
    <div class="buttons">
        <%
            String i = (String.valueOf(request.getAttribute(ATTR_LESSON_SERIAL_NUMBER)));
            int number = Integer.parseInt(i);

            String y = (String.valueOf(request.getAttribute(ATTR_LESSON_SIZE)));
            int num = Integer.parseInt(y);
        %>
         
        <button class="btnl" onclick="window.location = '${cancelUrl}';
                return false;"><spring:message code="common-btn-cancel" /></button> 
        <%
                  if (number != 1) { %>
        <button class="btnl" onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back-arrow" /></button><%
                    }
        %> 
        <%
                if (num != number) {%>
        <button class="btnl" onclick="window.location = '${nextUrl}';
                return false;"><spring:message code="common-btn-next-arrow" /></button><%}
        %> 
     </div>
</div>