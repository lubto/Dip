<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="eu.lms.iface.dto.MultipleChoiceDto"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="java.util.List"%>
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

    List<MultipleChoiceDto> question = (List) request.getAttribute(ATTR_ALL_MULTIPLE);
    
    String searchAttr = " ";
    searchAttr = (String) request.getAttribute(SEARCH_ATTR);
 
    PortletURL iteratorURL = renderResponse.createRenderURL();
    iteratorURL.setParameter(PARAM_PAGE, PAGE_ALL_Q);
    iteratorURL.setParameter(PARAM_ID_USER, idCurrentUser);
    iteratorURL.setParameter(ATTR_SEARCH, searchAttr);
%>
 
<portlet:renderURL var="createUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_CREATE_Q%>" /> 
</portlet:renderURL>
<portlet:actionURL name="<%= ACTION_SEARCH_QUESTION%>" var="getSearchResultURL" >  
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" />    
</portlet:actionURL>
<div class="admin-right">  

    <span class="sideMape"><spring:message code="side-map-queston" /> > <spring:message code="side-map-view" /></span>  

    <liferay-ui:success key="SuccessKey" message="lms-msg-dummy-created" />
    <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" /> 
    <liferay-ui:success key="SuccessKeyDeleted" message="lms-msg-dummy-deleted" />
    <liferay-ui:success key="SuccessKeyAdd" message="lms-msg-question-add" /> 
     <liferay-ui:success key="SuccessKeyremoved" message="lms-msg-removed" />

    <h3><spring:message code="mal-all-questions" /></h3>  
    
    <form action="${getSearchResultURL.toString()}" method="post"  class="tab-le">
        <input type="text" name="ASearchRequest" value='<%= (searchAttr == "") ? "" : searchAttr%>' class="in-put" >
        <spring:message code="btn-search" var="var_search"/>
        <input class="btnl" type="submit" value="${var_search}">
    </form>
    <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" iteratorURL="<%= iteratorURL%>" delta="5" deltaConfigurable="true" total="<%= question.size()%>">
        <liferay-ui:search-container-results>
            <%                
                results = ListUtil.subList(question, searchContainer.getStart(), searchContainer.getEnd());
                total = question.size();

                pageContext.setAttribute("results", results);
                pageContext.setAttribute("total", total);
            %>
        </liferay-ui:search-container-results>  
        <liferay-ui:search-container-row className="eu.lms.iface.dto.MultipleChoiceDto" modelVar="item" keyProperty="id">
            <liferay-ui:search-container-column-text name="question">
                <c:out value="${item.questionShort}" />
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text name="points">
                <c:out value="${item.points}" />
            </liferay-ui:search-container-column-text> 
            <liferay-ui:search-container-column-text name="lms-label-actions">
                <portlet:renderURL var="adUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ADD_TO_BANK%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
                </portlet:renderURL>
                <portlet:renderURL var="detailUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DETAIL_QUESTION%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                </portlet:renderURL>
                <portlet:renderURL var="editUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_FORM_MULTIPLECHOICE%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>
                <portlet:actionURL name="<%= ACTION_DELETE_Q_T_F%>" var="deleteUrl">
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                </portlet:actionURL>
                <portlet:renderURL var="deleteQuestionUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DELETE_Q_TRUE_FALSE%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${itemid}" /> 
                </portlet:renderURL>
                <spring:message code="map-add-to-bank" var="var1" />
                <liferay-ui:icon-menu> 
                    <liferay-ui:icon image="add" url="${adUrl}" message="${var1}" />
                    <liferay-ui:icon image="view" url="${detailUrl}" />
                    <liferay-ui:icon image="edit" url="${editUrl}" />
                    <li class role="presentation" >
                        <a class="taglib-icon" href="${deleteQuestionUrl}" onclick="if (confirm('<spring:message code="lms-msg-dele-question" arguments="${item.question}" htmlEscape="true" />')) {
                                window.location = '${deleteUrl}';
                            }
                            return false;">
                            <liferay-ui:icon image="delete" />
                        </a>
                    </li>
                </liferay-ui:icon-menu> 
            </liferay-ui:search-container-column-text>
        </liferay-ui:search-container-row> 
        <liferay-ui:search-iterator/>
    </liferay-ui:search-container> 
        <div class="buttons">
            <button class="btnl" onclick="window.location = '${createUrl}';"><spring:message code="common-btn-create-q" /></button>
        </div>      
</div> 