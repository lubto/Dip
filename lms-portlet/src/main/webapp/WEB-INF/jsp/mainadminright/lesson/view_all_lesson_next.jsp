<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="../../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %> 
<%
    ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
    String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:renderURL var="nextUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_LESSON_NEXT%>" />
    <portlet:param name="<%= PARAM_ID%>" value="${(lessonPto.serialNumber)+1}" /> 
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> 
</portlet:renderURL> 
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_LESSON_NEXT%>" />
    <portlet:param name="<%= PARAM_ID%>" value="${(lessonPto.serialNumber)-1}" />
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> 
</portlet:renderURL>
<portlet:renderURL var="cancelUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_LESSON%>" />  
    <portlet:param name="<%= PARAM_ID%>" value="${idCourse}" />
</portlet:renderURL> 
<portlet:renderURL var="backToCourseUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" /> 
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<div class="admin-right"> 
    <span class="sideMape"><a href="${backToCourseUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" /> > <a href="${cancelUrl}"><spring:message code="title-lesson" /></a> > <spring:message code="lable-view-all-lesson-next" /></span>
    <h3><spring:message code="mal-btn-show-lessons"/></h3>
    <table width="100%" >   
        <tr>  
            <td width="5%"></td>
            <td><c:out value="${lessonPto.content}" escapeXml="false"/></td> 
        </tr> 
        <tr>  
            <td></td> 
            <td style="text-align: right; "><spring:message code="page" /> <c:out value="${lessonPto.serialNumber}" /></td> 
        </tr>
    </table>  
    <div class="buttons">
        <%
            String i = (String.valueOf(request.getAttribute(ATTR_LESSON_SERIAL_NUMBER)));
            int number = Integer.parseInt(i);

            String y = (String.valueOf(request.getAttribute(ATTR_LESSON_SIZE)));
            int num = Integer.parseInt(y);
        %>
        <button class="btnl"  onclick="window.location = '${cancelUrl}';
                 return false;"><spring:message code="common-btn-cancel" /></button>
        <%
            if (number != 1) { %>
        <button class="btnl"  onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back-arrow" /></button><%
                    }
        %> 
        <%
            if (num != number) {%>
        <button class="btnl"  onclick="window.location = '${nextUrl}';
                return false;"><spring:message code="common-btn-next-arrow" /></button><%}
        %>  

    </div>
</div>