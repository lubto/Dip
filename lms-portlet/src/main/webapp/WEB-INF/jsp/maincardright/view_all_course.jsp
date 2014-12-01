<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="eu.lms.iface.dto.CourseItemDto"%>
<%@page import="javax.portlet.PortletURL"%> 
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf" %>   
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %>
<%
    List<CourseItemDto> list = (List) request.getAttribute(ATTR_ALL_ITEMS);

    String searchAttr = "";
    searchAttr = (String) request.getAttribute(ATTR_SEARCH);
    String userId = request.getRemoteUser();

    PortletURL iteratorURL = renderResponse.createRenderURL();
    iteratorURL.setParameter(PARAM_PAGE, PAGE_ALL_COURSE);
    iteratorURL.setParameter(ATTR_SEARCH, searchAttr);
    iteratorURL.setParameter(ATTR_CUR_ID_USER, userId);
%> 
<portlet:actionURL name="<%= ACTION_SEARCH_ALL_COURSE%>" var="getSearchResultURL" > 
</portlet:actionURL>
<div class="user-right">  
    <span class="sideMape"><spring:message code="side-map-your-courses" /> > <spring:message code="side-map-view"/></span> 
    <liferay-ui:success key="SuccessKey" message="msg-course-add" />
    <h3><spring:message code="title-course-to-study" /></h3>

    <form action="${getSearchResultURL.toString()}" method="post"  class="tab-le">
        <input type="text" name="ASearchRequest" value='<%= (searchAttr == "") ? "" : searchAttr%>' class="in-put" >
        <spring:message code="btn-search" var="var_search"/>
        <input class="btnl" type="submit" value="${var_search}">
    </form>
    <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" iteratorURL="<%= iteratorURL%>" delta="5" deltaConfigurable="true" total="<%= list.size()%>">
         <liferay-ui:search-container-results>
            <%                
                results = ListUtil.subList(list, searchContainer.getStart(), searchContainer.getEnd());
                total = list.size();

                pageContext.setAttribute("results", results);
                pageContext.setAttribute("total", total);
            %>
        </liferay-ui:search-container-results>   
        <liferay-ui:search-container-row className="eu.lms.iface.dto.CourseItemDto" modelVar="item" keyProperty="id"> 
            <liferay-ui:search-container-column-text name="state-course"> 
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
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text name="name">
                <c:out value="${item.nameCourse}" />
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
            <liferay-ui:search-container-column-text name="lms-label-actions">
                <portlet:renderURL var="startUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_START_COURSE%>" />
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${item.courseId}" />
                    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
                    <portlet:param name="<%= ATTR_ID_COURSE_ITEM%>" value="${item.id}" /> 
                    <portlet:param name="<%= ATTR_VISITED_COURSE%>" value="${item.visitedCourse}" />
                </portlet:renderURL>
                <portlet:renderURL var="testUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_TESTS%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.courseId}" />
                    <portlet:param name="<%= ATTR_ID_COURSE_ITEM%>" value="${item.id}" /> 
                    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
                </portlet:renderURL> 
                <portlet:renderURL var="detailUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_INFO_COURSE%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.courseId}" />  
                    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
                </portlet:renderURL>  
                <portlet:renderURL var="dmsUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DMS%>" />  
                    <portlet:param name="<%= PARAM_ID%>" value="${item.courseId}" />
                    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
                </portlet:renderURL> 
                <spring:message code="common-btn-enter" var="label"/>
                <spring:message code="common-btno-info" var="label1"/>
                <spring:message code="common-btn-document" var="label2"/>
                <spring:message code="common-btn-test" var="label3"/> 
                <c:choose>
                    <c:when test="${item.timesUp == true}">
                        <liferay-ui:icon-menu disabled="true"> 
                            <liferay-ui:icon image="submit" url="${startUrl}" message="${label}" />
                            <liferay-ui:icon image="view" url="${detailUrl}" message="${label1}"/>
                        </liferay-ui:icon-menu>
                    </c:when> 
                    <c:otherwise>
                        <liferay-ui:icon-menu> 
                            <liferay-ui:icon image="submit" url="${startUrl}" message="${label}" />
                            <liferay-ui:icon image="view" url="${detailUrl}" message="${label1}"/>
                            <liferay-ui:icon image="folder_full_document" url="${dmsUrl}" message="${label2}"/>
                            <liferay-ui:icon image="checked" url="${testUrl}" message="${label3}" />  
                        </liferay-ui:icon-menu>
                    </c:otherwise>
                </c:choose> 
            </liferay-ui:search-container-column-text>
        </liferay-ui:search-container-row> 
        <liferay-ui:search-iterator />
    </liferay-ui:search-container>  
    <div id="legend1">
        <img src="<%= request.getContextPath()%>/images/green.png" title="<spring:message code="state-visited" />" >
        &nbsp;<spring:message code="state-visited-description" /><br/>
        <img src="<%= request.getContextPath()%>/images/blue.png" title="<spring:message code="state-unvisited" />" >
        &nbsp;<spring:message code="state-unvisited-description" /><br/>
        <img src="<%= request.getContextPath()%>/images/red.png" title="<spring:message code="state-times-up" />" >
        &nbsp;<spring:message code="state-times-up-description" />
    </div>
</div>