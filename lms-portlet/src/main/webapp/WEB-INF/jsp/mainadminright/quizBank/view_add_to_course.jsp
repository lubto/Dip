<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
    ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
    String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:actionURL var="saveUrl" name="<%= ACTION_SAVE_ADD_TO_COURSE%>"> 
    <portlet:param name="<%= PARAM_ID%>" value="${entityId}" /> 
</portlet:actionURL>
<portlet:renderURL var="backUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_Q_BANK%>" /> 
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<div class="admin-right"> 

    <span class="sideMape"><spring:message code="side-map-queston" /> > <a href="${backUrl}"><spring:message code="side-map-bank" /></a> > <spring:message code="side-map-add-to-course" /></span>  

    <h3><spring:message code="map-add-to-course" /></h3>
    <c:set var="ns"><portlet:namespace/></c:set>
    <liferay-ui:error key="ErrorKey" message="common-msg-err-form-contains-errors" />

    <form id="fm" action="${saveUrl}" method="post" name="fm">
        <table width="100%">
            <tr>
                <td width="20%"></td>
                <td width="17%"><spring:message code="map-add-to-course"/></td>
                <td>
                    <select name="coursePto" id="coursePto">  
                        <c:forEach items="${coursePto}" var="item">
                            <option value="${item.id}" ><c:out value="${item.name}" /></option>
                        </c:forEach>
                    </select>     
                </td>
            </tr> 
        </table>

        <spring:message code="common-btn-add" var="submitText"/>
        <br/><br/>
        <button class="btnl" onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back" /></button> 
        <c:if test="${isNull == false}" >
            <input class="btnl" type="submit" value="${submitText}" /> 
        </c:if>  
    </form>  
</div>