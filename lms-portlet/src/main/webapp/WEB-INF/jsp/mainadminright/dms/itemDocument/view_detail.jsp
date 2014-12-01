<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
        ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
        String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:renderURL var="createUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_Q_ADD_ITEM_DOC%>" />
    <portlet:param name="<%= PARAM_ID_GROUP%>" value="${idGroup}" />
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
</portlet:renderURL>
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_GROUP_DOC%>" /> 
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " /> 
</portlet:renderURL> 
<portlet:renderURL var="backToCourseUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />  
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<div class="admin-right"> 
    <span class="sideMape"><a href="${backToCourseUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" />  > <a href="${backUrl}"><spring:message code="mal-all-group-documents" /></a> > <spring:message code="label-detail" /></span> 
    <h3><spring:message code="title-materials-for-study" /></h3> <br/>
    <liferay-ui:success key="SuccessKey" message="lms-msg-dummy-created" />
    <h4><c:out value="${itemDoc}" /></h4>
    <h4><c:out value="${groupDocPto.name}" /></h4>
    <p style="text-align: justify;"><c:out value="${groupDocPto.description}" escapeXml="false"/></p><br/>
    <table id="tableDms">
        <c:set var="number" value="${1}" />
        <c:set var="size" value="${sizeItems}" /> 

        <c:forEach items="${itemDocPto}" var="item" >
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
    <br/><br/>
    <button class="btnl"  onclick="window.location = '${backUrl}';
        return false;"><spring:message code="common-btn-back" /></button>
    <button class="btnl"  onclick="window.location = '${createUrl}';
                return false;"><spring:message code="common-btn-create" /></button>
</div>