<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="eu.lms.iface.dto.CourseItemDto"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.service.UserServiceUtil"%> 
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.User"%>
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
    List<CourseItemDto> courseItem = (List) request.getAttribute(ATTR_ALL_ITEMS);

    String searchAttr = "";
    searchAttr = (String) request.getAttribute(ATTR_SEARCH);
    Long courseId = (Long) request.getAttribute(PARAM_ID);
    
    PortletURL iteratorURL = renderResponse.createRenderURL();
    iteratorURL.setParameter(PARAM_PAGE, PAGE_VIEW_USERS_IN_COURSE);
    iteratorURL.setParameter(ATTR_SEARCH, searchAttr);
    iteratorURL.setParameter(PARAM_ID, courseId.toString()); 
%>
<portlet:renderURL var="addUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ADD_COURSE_TO_USER%>" /> 
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${entityId}" />  
</portlet:renderURL> 
<portlet:renderURL var="backUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${entityId}" />  
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>
<portlet:actionURL name="<%= ACTION_SEARCH_COURSE_ITEM%>" var="getSearchResultURL" >   
    <portlet:param name="<%= PARAM_ID%>" value="${entityId}" />  
</portlet:actionURL>

<div class="admin-right"> 
    <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" /> 
    <liferay-ui:success key="SuccessKeys" message="lms-msg-users-add" />
    <liferay-ui:success key="SuccessKey" message="lms-msg-user-add" />  
    <liferay-ui:success key="KeyRemoved" message="lms-msg-removed-user-course" />
    <liferay-ui:success key="KeyDeactivated" message="msg-deactivate" />  
    <liferay-ui:success key="KeyActivated" message="msg-activate" /> 

    <span class="sideMape"><a href="${backUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" /> > <spring:message code="label-user" /></span> 

    <h3><spring:message code="title-users-in-course" /></h3>

    <form action="${getSearchResultURL.toString()}" method="post"  class="tab-le">
        <input type="text" name="ASearchRequest" value='<%= (searchAttr == "") ? "" : searchAttr%>' class="in-put" >
        <spring:message code="btn-search" var="var_search"/>
        <input class="btnl" type="submit" value="${var_search}">
    </form>
    <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" iteratorURL="<%= iteratorURL%>" delta="5" deltaConfigurable="true" total="<%= courseItem.size()%>">
        <liferay-ui:search-container-results>
            <%
                results = ListUtil.subList(courseItem, searchContainer.getStart(), searchContainer.getEnd());
                total = courseItem.size();

                pageContext.setAttribute("results", results);
                pageContext.setAttribute("total", total);
            %> 
        </liferay-ui:search-container-results>
        <liferay-ui:search-container-row className="eu.lms.iface.dto.CourseItemDto" modelVar="item" keyProperty="id"> 
            <liferay-ui:search-container-column-text name="lable-user-full-name">
                <c:out value="${item.fullName}" />
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text name="state-course"> <span style="text-align: center;">
                    <c:choose>
                        <c:when test="${item.finishedCourse == true}"> 
                            <img src="<%= request.getContextPath()%>/images/finish.png" title="<spring:message code="state-finished" />">
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${item.timesUp == true}"> 
                                    <img src="<%= request.getContextPath()%>/images/red.png" title="<spring:message code="state-times-up" />">
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${item.visitedCourse == true}"> 
                                            <img src="<%= request.getContextPath()%>/images/green.png" title="<spring:message code="state-visited" />">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<%= request.getContextPath()%>/images/blue.png" title="<spring:message code="state-unvisited" />" > 
                                        </c:otherwise> 
                                    </c:choose>
                                </c:otherwise> 
                            </c:choose> 
                        </c:otherwise>
                    </c:choose>       </span>      
                </liferay-ui:search-container-column-text>
                <liferay-ui:search-container-column-text name="startCourse">
                    <c:set value="${item.startCourse}" var="startDate" />
                    <%
                        Date dateStart = (Date) pageContext.getAttribute("startDate");
                        // String date2 = String.valueOf(dateStart); 
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String dt = df.format(dateStart);
                    %>
                    <c:out value="<%= dt%>" />  
                    <%-- <c:out value="${item.startCourse}" /> --%>
                </liferay-ui:search-container-column-text>
                <liferay-ui:search-container-column-text name="endCourse">
                    <c:set value="${item.endCourse}" var="endDate" />
                    <%
                        Date dateEnd = (Date) pageContext.getAttribute("endDate");
                        DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                        String dt1 = df1.format(dateEnd);
                    %>
                    <c:out value="<%= dt1%>" />  
                    <%--  <c:out value="${item.endCourse}" /> --%>
                </liferay-ui:search-container-column-text>   
                <liferay-ui:search-container-column-text name="lable-visible">
                    <c:choose>
                        <c:when test="${item.visible == true}">
                            <spring:message code="lms-msg-visible" htmlEscape="true" />
                        </c:when>
                        <c:otherwise>
                            <spring:message code="lms-msg-unvisible" htmlEscape="true" />
                        </c:otherwise> 
                    </c:choose>  
                </liferay-ui:search-container-column-text> 
                <liferay-ui:search-container-column-text name="lms-label-actions">

                <portlet:renderURL var="allUsersCourseUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_USER_COURSES%>" />
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${entityId}" />  
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
                </portlet:renderURL>  
                <portlet:renderURL var="editUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_FORM_COURSE_ITEM%>" />  
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${entityId}" /> 
                </portlet:renderURL>  
                <portlet:actionURL name="<%= ACTION_REMOVE_COURSE_ITEM%>" var="deleteUrl">
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${entityId}" /> 
                </portlet:actionURL>
                <portlet:actionURL name="<%= ACTION_DE_ACTIVATE_COURSE_ITEM%>" var="deactivateUrl"> 
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${entityId}" /> 
                    <portlet:param name="<%= PARAM%>" value="1" />
                </portlet:actionURL>
                <portlet:actionURL name="<%= ACTION_DE_ACTIVATE_COURSE_ITEM%>" var="activateUrl">  
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${entityId}" /> 
                    <portlet:param name="<%= PARAM%>" value="0" />
                </portlet:actionURL>  
                <spring:message code="lms-msg-view-all-user-courses" var="var0" />
                <spring:message code="mal-deactivate-account-user" var="var1" />
                <spring:message code="mal-activate-account-user" var="var2" />
                <spring:message code="map-remove-user-from-course" var="var3" />
                <liferay-ui:icon-menu>  
                    <liferay-ui:icon image="preview" url="${allUsersCourseUrl}" message="${var0}" />
                    <liferay-ui:icon image="edit" url="${editUrl}" /> 
                    <c:choose>
                        <c:when test="${item.visible == true}">
                            <liferay-ui:icon image="activate" url="${activateUrl}" message="${var1}" /> 
                        </c:when>
                        <c:otherwise>
                            <liferay-ui:icon image="deactivate" url="${deactivateUrl}" message="${var2}" /> 
                        </c:otherwise> 
                    </c:choose>  
                    <li class role="presentation" >
                        <a class="taglib-icon" href="${deleteQuestionUrl}" onclick="if (confirm('<spring:message code="lms-msg-remove-user" arguments="${item.fullName}" htmlEscape="true" />')) {
                                    window.location = '${deleteUrl}';
                                }
                                return false;">
                            <liferay-ui:icon image="delete" message="${var3}"/>
                        </a></li>
                    </liferay-ui:icon-menu> 
                </liferay-ui:search-container-column-text>
            </liferay-ui:search-container-row> 
            <liferay-ui:search-iterator />
        </liferay-ui:search-container>  
    <br/><br/>
    <div class="buttons">
        <button class="btnl"  onclick="window.location = '${backUrl}';"><spring:message code="mal-btn-back-course" /></button>
        <button class="btnl"  onclick="window.location = '${addUrl}';"><spring:message code="mal-add-user-to-course" /></button> 
    </div>
    <table width="100%">
        <tr>
            <td></td>
            <td style="margin-right: 30px; float: right;" ><img src="<%= request.getContextPath()%>/images/green.png" title="<spring:message code="state-visited" />" >
                &nbsp;<spring:message code="state-visited-description" /><br/>
                <img src="<%= request.getContextPath()%>/images/blue.png" title="<spring:message code="state-unvisited" />" >
                &nbsp;<spring:message code="state-unvisited-description" /><br/>
                <img src="<%= request.getContextPath()%>/images/red.png" title="<spring:message code="state-times-up" />" >
                &nbsp;<spring:message code="state-times-up-description" /><br/>
                <img src="<%= request.getContextPath()%>/images/finish.png" title="<spring:message code="state-finished" />" >
                &nbsp;<spring:message code="state-finished-description" /></td>
        </tr>
    </table>

</div>