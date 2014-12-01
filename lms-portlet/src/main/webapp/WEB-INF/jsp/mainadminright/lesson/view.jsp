<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="eu.lms.iface.dto.LessonDto"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
    ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
    String idCurrentUser = String.valueOf(currentUser.getUserId());
    List<LessonDto> list = (List) request.getAttribute(ATTR_ALL_ITEMS);

    Long userId = (Long) request.getAttribute(PARAM_ID_COURSE);
    PortletURL iteratorURL = renderResponse.createRenderURL();
    iteratorURL.setParameter(PARAM_PAGE, PAGE_ALL_LESSON);
    iteratorURL.setParameter(PARAM_ID, userId.toString());
%> 
<portlet:renderURL var="createUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_CREATE_FORM_LESSON%>" />
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
</portlet:renderURL>
<portlet:renderURL var="showLessonsUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DETAIL_LESSONS%>" />
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
</portlet:renderURL>
<portlet:renderURL var="backToCourseUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" /> 
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<div class="admin-right">  
    <liferay-ui:success key="SuccessKey" message="lms-msg-dummy-created" />
    <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" /> 
    <liferay-ui:success key="SuccessKeyDeleted" message="lms-msg-dummy-deleted" /> 
    <span class="sideMape"><a href="${backToCourseUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" /> > <spring:message code="title-lesson" /></span> 

    <h3><spring:message code="title-all-lesson" /></h3>  
    <c:choose>
        <c:when test="${isNull == false}">
            <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" iteratorURL="<%= iteratorURL%>" delta="5" deltaConfigurable="true" total="<%= list.size()%>" >
                <%-- <liferay-ui:search-container-results results="${allItems}" total="${fn:length(allItems)}" /> --%>
                <liferay-ui:search-container-results>
                    <%
                        results = ListUtil.subList(list, searchContainer.getStart(), searchContainer.getEnd());
                        total = list.size();

                        pageContext.setAttribute("results", results);
                        pageContext.setAttribute("total", total);
                    %>  
                </liferay-ui:search-container-results> 
                <liferay-ui:search-container-row className="eu.lms.iface.dto.LessonDto" modelVar="item" keyProperty="id">
                    <liferay-ui:search-container-column-text name="lable-name">
                        <c:out value="${item.name}" />
                    </liferay-ui:search-container-column-text>   
                    <liferay-ui:search-container-column-text name="lable-serialNumber">
                        <c:out value="${item.serialNumber}" />
                    </liferay-ui:search-container-column-text>
                    <liferay-ui:search-container-column-text name="label-position"> 
                        <portlet:actionURL name="<%= ACTION_UP_LESSON%>" var="upUrl">
                            <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> 
                        </portlet:actionURL>
                        <portlet:actionURL name="<%= ACTION_DOWN_LESSON%>" var="downUrl">                        
                            <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                        </portlet:actionURL>
                        <c:if test="${item.serialNumber != 1}">
                            <a href="${upUrl}"><img src="<%= request.getContextPath()%>/images/up.png"></a>
                            </c:if>
                            <c:if test="${item.serialNumber != fn:length(allItems)}">
                            <a href="${downUrl}"><img src="<%= request.getContextPath()%>/images/down.png"></a>
                            </c:if>
                        </liferay-ui:search-container-column-text> 
                        <liferay-ui:search-container-column-text name="lms-label-actions"> 
                            <portlet:renderURL var="detailUrl">
                                <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DETAIL_LESSON%>" />
                                <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                                <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                            </portlet:renderURL>
                            <portlet:renderURL var="editUrl">
                                <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_FORM_LESSON%>" />
                                <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                                <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                            </portlet:renderURL>
                            <portlet:actionURL name="<%= ACTION_DELETE_LESSON%>" var="deleteUrl">
                                <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                                <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                            </portlet:actionURL>
                            <portlet:renderURL var="deleteQuestionUrl">
                                <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DELETE_QUESTION_LESSON%>" />
                                <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                                <%-- <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> --%>
                            </portlet:renderURL> 
                            <liferay-ui:icon-menu>  
                                <liferay-ui:icon image="view" url="${detailUrl}" />
                                <liferay-ui:icon image="edit" url="${editUrl}" /> 
                            <li class role="presentation" >
                                <a class="taglib-icon" href="${deleteQuestionUrl}" onclick="if (confirm('<spring:message code="lms-msg-dele-question" arguments="${item.name}" htmlEscape="true" />')) {
                                    window.location = '${deleteUrl}';
                                }
                                return false;">
                                    <liferay-ui:icon image="delete" />
                                </a>
                            </li>
                        </liferay-ui:icon-menu> 
                    </liferay-ui:search-container-column-text>
                </liferay-ui:search-container-row> 
                <liferay-ui:search-iterator  />
            </liferay-ui:search-container>
        </c:when>
        <c:otherwise>
            <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" >
                <liferay-ui:search-container-results results="${allItems}" total="${fn:length(allItems)}" /> 
                <liferay-ui:search-container-row className="eu.lms.iface.dto.LessonDto" modelVar="item" > 
                </liferay-ui:search-container-row> 
                <liferay-ui:search-iterator  />
            </liferay-ui:search-container>
        </c:otherwise>
    </c:choose> 
    <br/>
    <div class="buttons">&nbsp;
        <button class="btnl"  onclick="window.location = '${createUrl}';"><spring:message code="mal-btn-new-lesson" /></button>&nbsp;
        <c:if test="${isNull == false}">
            <button class="btnl"  onclick="window.location = '${showLessonsUrl}';"><spring:message code="mal-btn-show-book" /></button>
        </c:if>
        &nbsp;<button class="btnl"  style="float: left;" onclick="window.location = '${backToCourseUrl}';"><spring:message code="mal-btn-back-course" /></button> 
    </div>
</div>