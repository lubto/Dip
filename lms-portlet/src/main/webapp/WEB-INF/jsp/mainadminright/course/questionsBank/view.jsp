<%@page import="eu.lms.iface.dto.BankQuestionsDto"%>
<%@page import="java.util.List"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %> 
<%@include file="../../../init.jspf" %>
<%
    ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
    String idCurrentUser = String.valueOf(currentUser.getUserId());

    List<BankQuestionsDto> questionBank = (List) request.getAttribute(ATTR_ALL_ITEMS);

    String searchAttr = " ";
    searchAttr = (String) request.getAttribute(SEARCH_ATTR);
    String idCourse = (String) request.getAttribute(PARAM_ID_COURSE);

    PortletURL iteratorURL = renderResponse.createRenderURL();
    iteratorURL.setParameter(PARAM_PAGE, PAGE_Q_BANK);
    iteratorURL.setParameter(PARAM_ID_COURSE, idCourse);
    //iteratorURL.setParameter(PARAM_ID_USER, idCurrentUser);
    //iteratorURL.setParameter(ATTR_SEARCH, searchAttr);
%> 
<portlet:renderURL var="backUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> 
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<div class="admin-right"> 

    <span class="sideMape"><a href="${backUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" /> > <spring:message code="side-map-bank" /></span> 

    <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" /> 
    <liferay-ui:success key="SuccessKeys" message="lms-msg-users-add" />
    <liferay-ui:success key="SuccessKey" message="lms-msg-user-add" />  
    <liferay-ui:success key="SuccessKeyRemoved" message="lms-msg-removed-user-course" />
    <liferay-ui:success key="KeyDeactivated" message="msg-deactivate" />  
    <liferay-ui:success key="KeyActivated" message="msg-activate" />  
    <h3><spring:message code="mal-all-q-bank" /></h3>
 <c:choose>
        <c:when test="${isNull == false}">
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
                    <liferay-ui:search-container-column-text name="label-position"> 
                        <portlet:actionURL name="<%= ACTION_UP_QBANK%>" var="upUrl">
                            <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> 
                        </portlet:actionURL>
                        <portlet:actionURL name="<%= ACTION_DOWN_QBANK%>" var="downUrl">                        
                            <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                        </portlet:actionURL>
                          <c:out value="${item.serialNumber}" />
                        <c:if test="${item.serialNumber != 1}">
                            <a href="${upUrl}"><img src="<%= request.getContextPath()%>/images/up.png"></a>
                            </c:if>
                            <c:if test="${item.serialNumber != fn:length(allItems)}">
                            <a href="${downUrl}"><img src="<%= request.getContextPath()%>/images/down.png"></a>
                            </c:if>
                        </liferay-ui:search-container-column-text> 
            <liferay-ui:search-container-column-text name="lms-label-actions"> 
                <portlet:renderURL var="removeQuestionUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_REMOVE_Q_FROM_COURSE%>" />
                    <portlet:param name="<%= ATTR_QUIZ_BANK_PTO%>" value="${itemid}" />  
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${item.course.id}" /> 
                </portlet:renderURL> 
                <portlet:actionURL name="<%= ACTION_REMOVE_Q_BANK_FROM_COURSE%>" var="removeUrl">
                    <portlet:param name="<%= ATTR_QUIZ_BANK_PTO%>" value="${item.id}" /> 
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${item.course.id}" /> 
                </portlet:actionURL>
                <portlet:renderURL var="viewUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_VIEW_Q_IN_BANK%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> 
                    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
                </portlet:renderURL>
                <portlet:renderURL var="editUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_Q_BANK_IN_COURSE%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> 
                </portlet:renderURL> 
                <spring:message code="map-all-questions" var="var2" />
                <spring:message code="map-remove-from-course" var="var3" />
                <liferay-ui:icon-menu>    

                    <liferay-ui:icon image="all_pages" url="${viewUrl}" message="${var2}" />
                    <liferay-ui:icon image="edit" url="${editUrl}" /><%----%><li class role="presentation" >
                        <a class="taglib-icon" href="${removeQuestionUrl}" onclick="if (confirm('<spring:message code="lms-msg-remove-bank" arguments="${item.name}" htmlEscape="true" />')) {
                                    window.location = '${removeUrl}';
                                }
                                return false;">
                            <liferay-ui:icon image="remove" message="${var3}"/>
                        </a> 
                        <%-- --%></li>  
                    </liferay-ui:icon-menu> 
                </liferay-ui:search-container-column-text>
            </liferay-ui:search-container-row> 
            <liferay-ui:search-iterator/>
        </liferay-ui:search-container>
  </c:when>
        <c:otherwise>
            <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" >
                <liferay-ui:search-container-results results="${allItems}" total="${fn:length(allItems)}" /> 
                <liferay-ui:search-container-row className="eu.lms.iface.dto.BankQuestionsDto" modelVar="item" > 
                </liferay-ui:search-container-row> 
                <liferay-ui:search-iterator  />
            </liferay-ui:search-container>
        </c:otherwise>
    </c:choose> 
        <div class="buttons">
            <button class="btnl" onclick="window.location = '${backUrl}';"><spring:message code="common-btn-back" /></button>
        </div>

</div>