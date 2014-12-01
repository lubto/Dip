<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
        ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
        String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:actionURL name="<%= ACTION_CREATE_Q_BANK%>" var="createUrl" /> 
<portlet:renderURL var="backUrl" >
  <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_Q_BANK%>" />
   <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
   <portlet:param name="<%= ATTR_SEARCH%>" value=" " /> 
</portlet:renderURL>
<div class="admin-right"> 

    <span class="sideMape"><spring:message code="side-map-queston" /> > <a href="${backUrl}"><spring:message code="side-map-bank" /></a> > <c:out value="${shortcutName}"/> > <spring:message code="side-map-view-q" /></span>  
    
     <liferay-ui:success key="SuccessKey" message="lms-msg-dummy-created" />
    <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" /> 
    <liferay-ui:success key="SuccessKeyremoved" message="lms-msg-removed" />
    
    <h3><spring:message code="title-q-bank" arguments="${shortcutName}" /></h3>
    
    <!-- view question type of Multiple choice-->
    <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items">
        <liferay-ui:search-container-results results="${itemsAllMultiple}" total="${fn:length(itemsAllMultiple)}" /> 
        <liferay-ui:search-container-row className="eu.lms.iface.dto.MultipleChoiceDto" modelVar="item3">
            <liferay-ui:search-container-column-text name="question">
                <c:out value="${item3.question}" />
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text name="points">
                <c:out value="${item3.points}" />
            </liferay-ui:search-container-column-text> 
            <liferay-ui:search-container-column-text name="lms-label-actions"> 
                <portlet:renderURL var="viewCUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DETAIL_QUESTION%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item3.id}" />
                    <portlet:param name="<%= PARAM_TYPE_Q%>" value="MultipleChoice" />
                </portlet:renderURL> 
                <portlet:actionURL name="<%= ACTION_REMOVE_Q_FROM_BANK%>" var="removeCUrl">
                    <portlet:param name="<%= PARAM_ID%>" value="${item3.id}" />
                    <portlet:param name="<%= ATTR_QUIZ_BANK_PTO%>" value="${quizBankPto}" />
                    <portlet:param name="<%= PARAM_TYPE_Q%>" value="MultipleChoice" />
                </portlet:actionURL>
                <portlet:renderURL var="editUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_FORM_MULTIPLECHOICE%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>
                <portlet:renderURL var="removeCQuestionUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_REMOVE_Q_FROM_BANK%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item3id}" />
                    <portlet:param name="<%= ATTR_QUIZ_BANK_PTO%>" value="${quizBankPto}" />
                    <portlet:param name="<%= PARAM_TYPE_Q%>" value="MultipleChoice" />
                </portlet:renderURL> 
                <liferay-ui:icon-menu> 
                    <liferay-ui:icon image="view" url="${viewCUrl}" />  
                    <liferay-ui:icon image="edit" url="${editUrl}" />
              <li class role="presentation" >
                    <a class="taglib-icon" href="${removeCQuestionUrl}" onclick="if (confirm('<spring:message code="lms-msg-remove-question" arguments="${item3.question}" htmlEscape="true" />')) {
                                window.location = '${removeCUrl}';
                            }
                            return false;">
                        <liferay-ui:icon image="remove" />
                    </a>
                </li>
                    </liferay-ui:icon-menu> 
            </liferay-ui:search-container-column-text>
        </liferay-ui:search-container-row> 
        <liferay-ui:search-iterator />
    </liferay-ui:search-container>
    
     <div class="buttons"> 
        <button class="btnl" onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back" /></button>
    </div> 
</div>