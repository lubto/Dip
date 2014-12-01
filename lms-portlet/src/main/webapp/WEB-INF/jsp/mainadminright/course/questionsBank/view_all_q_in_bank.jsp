<%@page import="javax.portlet.PortletContext"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="eu.lms.iface.dto.MultipleChoiceDto"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
    PortletContext pc = renderRequest.getPortletSession().getPortletContext();

    ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
    String idCurrentUser = String.valueOf(currentUser.getUserId());

    List<MultipleChoiceDto> question = new ArrayList<MultipleChoiceDto>();
    question = (List) request.getAttribute(ATTR_ALL_ITEMS);

    String searchAttr = " ";
    searchAttr = (String) request.getAttribute(SEARCH_ATTR);
    Long idCourse = (Long) request.getAttribute(PARAM_ID_COURSE);
    Long id = (Long) request.getAttribute(ATTR_QUIZ_BANK_PTO);

    PortletURL iteratorURL = renderResponse.createRenderURL();
    iteratorURL.setParameter(PARAM_PAGE, PAGE_VIEW_Q_IN_BANK);
    iteratorURL.setParameter(PARAM_ID_USER, idCurrentUser);
    iteratorURL.setParameter(ATTR_SEARCH, searchAttr);
    iteratorURL.setParameter(PARAM_ID_COURSE, idCourse.toString());
    iteratorURL.setParameter(PARAM_ID, id.toString());
%>

<portlet:actionURL name="<%= ACTION_SEARCH_QUESTIONS_IN_BANK%>" var="getSearchResultURL" >  
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />  
    <portlet:param name="<%= PARAM_ID%>" value="${quizBankPto}" />
    <%--<portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> --%> 
</portlet:actionURL>
<div class="admin-right">
    <portlet:renderURL var="backUrl" >
        <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_Q_BANK%>" />
        <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
        <portlet:param name="<%= ATTR_SEARCH%>" value=" " /> 
        <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> 
    </portlet:renderURL>
    <portlet:renderURL var="backToCourseUrl" >
        <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" /> 
        <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
        <portlet:param name="<%= ATTR_SEARCH%>" value=" " /> 
    </portlet:renderURL>
    <div class="admin-right"> 
        <span class="sideMape"><a href="${backToCourseUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" /> > <a href="${backUrl}"><spring:message code="side-map-bank" /></a> > <c:out value="${shortcutName}" /> > <spring:message code="side-map-view-q" /></span>  
        <liferay-ui:success key="SuccessKey" message="lms-msg-dummy-created" />
        <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" /> 
        <liferay-ui:success key="SuccessKeyremoved" message="lms-msg-removed" />
        <h3><spring:message code="title-q-bank" arguments="${shortcutName}" /></h3>

        <form action="${getSearchResultURL.toString()}" method="post"  class="tab-le">
            <input type="text" name="ASearchRequest" value='<%= (searchAttr == "") ? "" : searchAttr%>' class="in-put" >
            <spring:message code="btn-search" var="var_search"/>
            <input class="btnl" type="submit" value="${var_search}">
        </form>
        <c:choose>
            <c:when test="${isNull == false}">  
                <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" iteratorURL="<%= iteratorURL%>" delta="5" deltaConfigurable="true" total="${fn:length(allItems)}">
                    <liferay-ui:search-container-results>
                        <%
                            results = ListUtil.subList(question, searchContainer.getStart(), searchContainer.getEnd());
                            total = question.size();

                            pageContext.setAttribute("results", results);
                            pageContext.setAttribute("total", total);
                        %>
                    </liferay-ui:search-container-results>
                    <liferay-ui:search-container-row className="eu.lms.iface.dto.MultipleChoiceDto" modelVar="item3" keyProperty="id">
                        <liferay-ui:search-container-column-text name="question">
                            <c:out value="${item3.questionShort}" />
                        </liferay-ui:search-container-column-text>
                        <liferay-ui:search-container-column-text name="points">
                            <c:out value="${item3.points}" />
                        </liferay-ui:search-container-column-text> 
                        <liferay-ui:search-container-column-text name="lms-label-actions">
                            <portlet:renderURL var="editUrl">
                                <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_Q_IN_COURSE%>" />
                                <portlet:param name="<%= PARAM_ID%>" value="${item3.id}" /> 
                                <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />  
                                <portlet:param name="<%= PARAM_ID_BANK%>" value="${quizBankPto}" />
                            </portlet:renderURL>
                            <portlet:renderURL var="viewCUrl">
                                <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DETAIL_QUESTION_IN_COURSE%>" />
                                <portlet:param name="<%= PARAM_ID%>" value="${item3.id}" /> 
                                <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />  
                                <portlet:param name="<%= PARAM_ID_BANK%>" value="${quizBankPto}" /> 
                            </portlet:renderURL> 
                            <portlet:actionURL name="<%= ACTION_REMOVE_Q_FROM_BANK_IN_COURSE%>" var="removeCUrl">
                                <portlet:param name="<%= PARAM_ID%>" value="${item3.id}" />
                                <portlet:param name="<%= ATTR_QUIZ_BANK_PTO%>" value="${quizBankPto}" />
                                <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> 
                            </portlet:actionURL>
                            <portlet:renderURL var="removeCQuestionUrl">
                                <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_REMOVE_Q_FROM_BANK%>" />
                                <portlet:param name="<%= PARAM_ID%>" value="${item3id}" />
                                <portlet:param name="<%= ATTR_QUIZ_BANK_PTO%>" value="${quizBankPto}" /> 
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
                    <liferay-ui:search-iterator/>
                </liferay-ui:search-container>
                 </c:when>
                 <c:otherwise>
                     <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" >
                         <liferay-ui:search-container-results results="${allItems}" total="${fn:length(allItems)}" /> 
                         <liferay-ui:search-container-row className="eu.lms.iface.dto.MultipleChoiceDto" modelVar="item" > 
                         </liferay-ui:search-container-row> 
                         <liferay-ui:search-iterator  />
                     </liferay-ui:search-container>
                 </c:otherwise>
             </c:choose>  
                <div class="buttons"> 
                    <button class="btnl" onclick="window.location = '${backUrl}';
                    return false;"><spring:message code="common-btn-back" /></button>
                </div> 
            </div>