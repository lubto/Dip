<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="eu.lms.iface.dto.CourseItemDto"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %> 
<%     ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
    String idCurrentUser = String.valueOf(currentUser.getUserId());
    
    List<CourseItemDto> courseItem = (List) request.getAttribute(ATTR_ALL_ITEMS);

    String searchAttr = "";
    searchAttr = (String) request.getAttribute(ATTR_SEARCH);
    Long courseId = (Long) request.getAttribute(PARAM_ID_COURSE);
    Long entiti = (Long) request.getAttribute(PARAM_ID);
    PortletURL iteratorURL = renderResponse.createRenderURL();
    iteratorURL.setParameter(PARAM_PAGE, PAGE_ALL_USER_COURSES);
    iteratorURL.setParameter(PARAM_ID_COURSE, courseId.toString());
    iteratorURL.setParameter(PARAM_ID, entiti.toString());
    iteratorURL.setParameter(ATTR_SEARCH, searchAttr);
%> 
<portlet:renderURL var="backUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_VIEW_USERS_IN_COURSE%>" />
    <portlet:param name="<%= PARAM_ID%>" value="${idCourse}" />  
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%=idCurrentUser%>" /> 
</portlet:renderURL>
<portlet:renderURL var="backToCourseUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%=idCurrentUser%>" /> 
</portlet:renderURL>
<portlet:actionURL name="<%= ACTION_SEARCH_USER_IN_COURSE%>" var="getSearchResultURL" >  
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> 
    <portlet:param name="<%= PARAM_ID%>" value="${entityId}" /> 
</portlet:actionURL>
<div class="admin-right">   
    <span class="sideMape"><a href="${backToCourseUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" /> > <a href="${backUrl}"><spring:message code="label-user" /></a> > <c:out value="${shortcutName}"/> > <spring:message code="lms-msg-view-all-user-courses"/></span> 

    <h3><spring:message code="title-register-course-user" arguments="${shortcutName}"/></h3>
    
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
            <liferay-ui:search-container-column-text name="label-course-name">
                <c:out value="${item.nameCourse}" />
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
            </liferay-ui:search-container-row> 
            <liferay-ui:search-iterator />
        </liferay-ui:search-container>  
    <br/><br/>
    <div class="buttons">
        <button class="btnl" onclick="window.location = '${backUrl}';"><spring:message code="common-btn-back" /></button>
        <%--  <button onclick="window.location = '${addUrl}';"><spring:message code="mal-add-user-to-course" /></button>  --%>
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