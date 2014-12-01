<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="eu.lms.iface.dto.CourseItemDto"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf" %>   
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %>
<%
    List<CourseItemDto> list = (List) request.getAttribute(ATTR_ALL_ITEMS); 
    String searchAttr = "";
    searchAttr = (String) request.getAttribute(ATTR_SEARCH);
    String userId = request.getRemoteUser();

    PortletURL iteratorURL = renderResponse.createRenderURL();
    iteratorURL.setParameter(PARAM_PAGE, PAGE_FINISH_COURSE);
    iteratorURL.setParameter(ATTR_SEARCH, searchAttr);
    iteratorURL.setParameter(ATTR_CUR_ID_USER, userId);
%> 
<portlet:actionURL name="<%= ACTION_SEARCH_ALL_COURSE_FINISH%>" var="getSearchResultURL" >  
</portlet:actionURL>
<div class="user-right">
    
    <span class="sideMape"><spring:message code="side-map-your-finish-course" /> > <spring:message code="side-map-view"/></span> 
    
    <h3><spring:message code="title-finish-course" /></h3>
    
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
            <liferay-ui:search-container-column-text name="name">
                <c:out value="${item.nameCourse}" />
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text name="startCourse">
                <c:out value="${item.startCourse}" />
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text name="endCourse">
                <c:out value="${item.endCourse}" />
            </liferay-ui:search-container-column-text>   
            <liferay-ui:search-container-column-text name="lms-label-actions"> 
                <portlet:renderURL var="testUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_INFO_Q%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.courseId}" />
                    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
                    <portlet:param name="<%= ATTR_ID_COURSE_ITEM%>" value="${item.id}" /> 
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
                <spring:message code="common-btno-info" var="label1"/>
                <spring:message code="common-btn-document" var="label2"/>
                <spring:message code="common-btn-test" var="label3"/>
                <liferay-ui:icon-menu> 
                    <c:if test="${item.visibleLessons == true}">
                    <liferay-ui:icon image="view" url="${detailUrl}" message="${label1}"/>
                    </c:if>
                    <c:if test="${item.visibleDocuments == true}">
                    <liferay-ui:icon image="folder_full_document" url="${dmsUrl}" message="${label2}"/>
                    </c:if>
                    <c:if test="${item.visibleQuestions == true}">
                    <liferay-ui:icon image="checked" url="${testUrl}" message="${label3}" />  
                    </c:if>
                </liferay-ui:icon-menu>
            </liferay-ui:search-container-column-text>
        </liferay-ui:search-container-row> 
        <liferay-ui:search-iterator />
    </liferay-ui:search-container>   
</div>