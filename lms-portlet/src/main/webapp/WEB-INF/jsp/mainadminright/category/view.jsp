<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="eu.lms.iface.dto.CategoryDto"%>
<%@page import="java.util.List"%>
<%@page import="javax.portlet.PortletContext"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.dao.search.RowChecker"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@include file="../../init.jspf" %> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>

<portlet:renderURL var="createUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_CREATE_FORM_CATEGORY%>" />
</portlet:renderURL>
   
<div class="admin-right">
    <liferay-ui:success key="SuccessKey" message="lms-msg-dummy-created" />
    <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" /> 
    <liferay-ui:success key="SuccessKeyDeleted" message="lms-msg-dummy-deleted" />
    <liferay-ui:success key="KeyDeactivated" message="msg-deactivate" />  
    <liferay-ui:success key="KeyActivated" message="msg-activate" /> 
    <liferay-ui:error key="ErrorKeyFull" message="lms-msg-full-category" /> 

    <span class="sideMape"><spring:message code="lable-category" /> > <spring:message code="label-view" /></span>  
    <h3><spring:message code="mal-all-category" /></h3>
 <%
        String searchRequest = ParamUtil.getString(request, "searchRequest");
        PortletContext pc = renderRequest.getPortletSession().getPortletContext();
        
        List<CategoryDto> listCategory = (List) request.getAttribute(ATTR_ALL_ITEMS);
        
        String searchAttr = "";
        searchAttr = (String) request.getAttribute(SEARCH_ATTR); 
       
        PortletURL iteratorURL = renderResponse.createRenderURL();  
        iteratorURL.setParameter(PARAM_PAGE, PAGE_ALL_CATEGORY); 
        iteratorURL.setParameter(ATTR_SEARCH, searchAttr);
    %> 
    <portlet:actionURL name="<%= ACTION_SEARCH_CATEGORY%>" var="getSearchResultURL" >  
    </portlet:actionURL>
    
    <form action="${getSearchResultURL.toString()}" method="post"  class="tab-le">
     <input type="text" name="ASearchRequest" value='<%= (searchAttr == "" ) ? "" : searchAttr%>' class="in-put" >
      <spring:message code="btn-search" var="var_search"/>
      <input class="btnl" type="submit" value="${var_search}">
 </form>
    <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" iteratorURL="<%= iteratorURL%>" delta="5" deltaConfigurable="true" total="<%= listCategory.size()%>" >
         <liferay-ui:search-container-results>
            <%                
                results = ListUtil.subList(listCategory, searchContainer.getStart(), searchContainer.getEnd());
                total = listCategory.size();

                pageContext.setAttribute("results", results);
                pageContext.setAttribute("total", total);
            %>
        </liferay-ui:search-container-results>     
        <liferay-ui:search-container-row className="eu.lms.iface.dto.CategoryDto" modelVar="item" keyProperty="id"> 
            <liferay-ui:search-container-column-text name="lable-name" > 
                <portlet:renderURL var="detailUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DETAIL_CATEGORY%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>
                <a class="noBlue" href="${detailUrl}"> <c:out value="${item.name}" /></a>
            </liferay-ui:search-container-column-text> 
            <liferay-ui:search-container-column-text name="lable-size-courses">
                <c:out value="${item.numberCourses}" />
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
            <liferay-ui:search-container-column-text name="lable-free">
                <c:choose>
                    <c:when test="${item.freeCourse == true}">
                        <spring:message code="yes" htmlEscape="true" />
                    </c:when>
                    <c:otherwise>
                        <spring:message code="no" htmlEscape="true" />
                    </c:otherwise> 
                </c:choose>  
            </liferay-ui:search-container-column-text>
            <% if (request.isUserInRole("LMS-administrator")) {
            %>
            <liferay-ui:search-container-column-text name="lms-label-actions">
                <portlet:renderURL var="viewCourseUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE_BY_CATEGORY%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
                </portlet:renderURL>
                <portlet:renderURL var="editUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_FORM_CATEGORY%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>
                <portlet:actionURL name="<%= ACTION_DELETE_CATEGORY%>" var="deleteUrl">
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:actionURL>
                <portlet:renderURL var="deleteQuestionUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DELETE_QUESTION_CATEGORY%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>
                <portlet:actionURL name="<%= ACTION_DE_ACTIVATE_CATEGORY%>" var="deactivateUrl"> 
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                    <portlet:param name="<%= PARAM%>" value="1" />
                </portlet:actionURL>
                <portlet:actionURL name="<%= ACTION_DE_ACTIVATE_CATEGORY%>" var="activateUrl">  
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                    <portlet:param name="<%= PARAM%>" value="0" />
                </portlet:actionURL> 
                <spring:message code="mal-deactivate-account-user" var="var2" />
                <spring:message code="mal-activate-account-user" var="var3" />
                <spring:message code="all-course-btn" var="var4" />
                <liferay-ui:icon-menu> 
                    <liferay-ui:icon image="view" url="${detailUrl}" />
                    <liferay-ui:icon image="edit" url="${editUrl}" /> 
                    <liferay-ui:icon image="all_pages" url="${viewCourseUrl}"  message="${var4}"/> 
                    <c:choose>
                        <c:when test="${item.visible == true}">
                            <liferay-ui:icon image="activate" url="${activateUrl}" message="${var2}" /> 
                        </c:when>
                        <c:otherwise>
                            <liferay-ui:icon image="deactivate" url="${deactivateUrl}" message="${var3}" /> 
                        </c:otherwise> 
                    </c:choose>  
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
            <%}%>
        </liferay-ui:search-container-row>  
        <liferay-ui:search-iterator  />
    </liferay-ui:search-container>
    <br/> 
    <% if (request.isUserInRole("LMS-administrator")) {
    %>
    <button class="btnl" onclick="window.location = '${createUrl}';"><spring:message code="mal-btn-new-category" /></button>
    <%}%>
</div>