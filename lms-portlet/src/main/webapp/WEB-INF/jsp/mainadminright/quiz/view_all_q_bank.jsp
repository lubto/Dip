<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="eu.lms.iface.dto.BankQuestionsDto"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>

<portlet:actionURL name="<%= ACTION_CREATE_Q_BANK%>" var="createUrl" /> 
<%
    ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
    String idCurrentUser = String.valueOf(currentUser.getUserId());

    List<BankQuestionsDto> questionBank = (List) request.getAttribute(ATTR_ALL_ITEMS);

    String searchAttr = " ";
    searchAttr = (String) request.getAttribute(SEARCH_ATTR);
 
    PortletURL iteratorURL = renderResponse.createRenderURL();
    iteratorURL.setParameter(PARAM_PAGE, PAGE_ALL_Q_BANK);
    iteratorURL.setParameter(PARAM_ID_USER, idCurrentUser);
    iteratorURL.setParameter(ATTR_SEARCH, searchAttr);
%>
<portlet:actionURL name="<%= ACTION_SEARCH_QUIZ_BANK%>" var="getSearchResultURL" >  
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" />    
</portlet:actionURL>
<div class="admin-right"> 

    <span class="sideMape"><spring:message code="side-map-queston" /> > <spring:message code="side-map-view-bank-q" /></span>  

    <liferay-ui:success key="SuccessKey" message="lms-msg-dummy-created" />
    <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" /> 
    <liferay-ui:success key="SuccessKeyDeleted" message="lms-msg-dummy-deleted" />
    <liferay-ui:success key="SuccessKeyAdd" message="lms-msg-add-qbank" /> 
     <liferay-ui:success key="SuccessKeyremoved" message="lms-msg-removed-qbank" />
    <liferay-ui:error key="ErrorKeyFullBank" message="lms-msg-full-bank" /> 

    <h3><spring:message code="mal-all-q-bank" /></h3>

    <form action="${getSearchResultURL.toString()}" method="post"  class="tab-le">
        <input type="text" name="ASearchRequest" value='<%= (searchAttr == "") ? "" : searchAttr%>' class="in-put" >
        <spring:message code="btn-search" var="var_search"/>
        <input class="btnl" type="submit" value="${var_search}">
    </form>
    <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" iteratorURL="<%= iteratorURL%>" delta="5" deltaConfigurable="true" total="<%= questionBank.size()%>">
        <liferay-ui:search-container-results>
            <%                
                results = ListUtil.subList(questionBank, searchContainer.getStart(), searchContainer.getEnd());
                total = questionBank.size();

                pageContext.setAttribute("results", results);
                pageContext.setAttribute("total", total);
            %>
        </liferay-ui:search-container-results> 
        <liferay-ui:search-container-row className="eu.lms.iface.dto.BankQuestionsDto" modelVar="item" keyProperty="id">
            <liferay-ui:search-container-column-text name="name">
                <c:out value="${item.name}" />
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text name="label-number-questions">
                <c:out value="${item.numberAllQ}" />
            </liferay-ui:search-container-column-text> 
            <liferay-ui:search-container-column-text name="course">
                <c:choose>
                    <c:when test="${!empty item.course}">
                        <c:out value="${item.course.name}" />
                    </c:when>
                    <c:otherwise>
                        <em><spring:message code="common-empty-value" /></em>
                    </c:otherwise>
                </c:choose> 
            </liferay-ui:search-container-column-text> 
            <liferay-ui:search-container-column-text name="lms-label-actions">
                <portlet:renderURL var="addUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ADD_TO_COURSE%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" />   
                </portlet:renderURL> 
                <portlet:renderURL var="addQuestionsUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ADD_MORE_QUESTIONS%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" />   
                </portlet:renderURL> 
                <portlet:renderURL var="removeQuestionUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_REMOVE_Q_FROM_COURSE%>" />
                    <portlet:param name="<%= ATTR_QUIZ_BANK_PTO%>" value="${itemid}" />  
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${item.course.id}" /> 
                    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
                </portlet:renderURL> 
                <portlet:actionURL name="<%= ACTION_REMOVE_Q_FROM_COURSE%>" var="removeUrl">
                    <portlet:param name="<%= ATTR_QUIZ_BANK_PTO%>" value="${item.id}" /> 
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${item.course.id}" /> 
                </portlet:actionURL>
                <portlet:renderURL var="viewUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_VIEW_ALL_Q_IN_BANK%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>
                <portlet:renderURL var="editUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_Q_BANK%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>
                <portlet:actionURL name="<%= ACTION_DELETE_Q_BANK%>" var="deleteUrl">
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:actionURL>
                <portlet:renderURL var="deleteQuestionUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DELETE_Q_BANK%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>
                <spring:message code="map-add-to-course" var="var1" />
                <spring:message code="map-all-questions" var="var2" />
                <spring:message code="map-remove-from-course" var="var3" />
                <spring:message code="mal-btn-add-more-questions-to-test" var="var4" />
                <liferay-ui:icon-menu>  
                    <liferay-ui:icon image="add" url="${addUrl}" message="${var1}" />
                      <liferay-ui:icon image="add_template_form" url="${addQuestionsUrl}" message="${var4}" />
                    <c:choose>
                        <c:when test="${!empty item.course}">
                            <li class role="presentation" >
                                <a class="taglib-icon" href="${removeQuestionUrl}" onclick="if (confirm('<spring:message code="lms-msg-remove-bank" arguments="${item.name}" htmlEscape="true" />')) {
                                            window.location = '${removeUrl}';
                                        }
                                        return false;">
                                    <liferay-ui:icon image="remove" message="${var3}"/>
                                </a> 
                            </li>
                        </c:when>
                        <c:otherwise>

                        </c:otherwise>
                    </c:choose> 

                    <liferay-ui:icon image="all_pages" url="${viewUrl}" message="${var2}" />
                    <liferay-ui:icon image="edit" url="${editUrl}" />
                    <li class role="presentation" >
                        <a class="taglib-icon" href="${deleteQuestionUrl}" onclick="if (confirm('<spring:message code="lms-msg-dele-question" arguments="${item.name}" htmlEscape="true" />')) {
                                    window.location = '${deleteUrl}';
                                }
                                return false;">
                            <liferay-ui:icon image="delete" />
                        </a>  <li>
                    </liferay-ui:icon-menu> 
                </liferay-ui:search-container-column-text>
            </liferay-ui:search-container-row> 
            <liferay-ui:search-iterator/>
        </liferay-ui:search-container>

        <div class="buttons">
            <button class="btnl" onclick="window.location = '${createUrl}';"><spring:message code="common-btn-create-bank" /></button>
        </div>
</div>