<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="eu.lms.iface.dto.CourseItemDto"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %> 
<portlet:renderURL var="backUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_USERS%>" />  
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />  
</portlet:renderURL>
<portlet:actionURL name="<%= ACTION_SEARCH_COURSE_ITEM_ACCOUNT%>" var="getSearchResultURL" >
    <portlet:param name="<%= PARAM_ID%>" value="${entityId}" />  
</portlet:actionURL>
<%
    List<CourseItemDto> course = (List) request.getAttribute(ATTR_ALL_ITEMS);
    Long id = (Long) request.getAttribute(PARAM_ID);
    String searchAttr = "";
    searchAttr = (String) request.getAttribute(SEARCH_ATTR);
    String userId = request.getRemoteUser();
    PortletURL iteratorURL = renderResponse.createRenderURL();
    iteratorURL.setParameter(PARAM_PAGE, PAGE_ITEMS_USER);
    iteratorURL.setParameter(ATTR_SEARCH, searchAttr);
    iteratorURL.setParameter(PARAM_ID_USER, userId);
    iteratorURL.setParameter(PARAM_ID, id.toString());
%> 
<div class="admin-right"> 
    <span class="sideMape"><a href="${backUrl}"><spring:message code="label-account" /></a> > <c:out value="${shortcutName}" /> > <spring:message code="label-view"/></span> 

    <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" /> 
    <liferay-ui:success key="SuccessKeys" message="lms-msg-users-add" />
    <liferay-ui:success key="SuccessKey" message="lms-msg-user-add" />  
    <liferay-ui:success key="KeyRemoved" message="lms-msg-removed-user-course" />
    <liferay-ui:success key="KeyDeactivated" message="msg-deactivate" />  
    <liferay-ui:success key="KeyActivated" message="msg-activate" /> 

    <h3><spring:message code="title-register-course-user" arguments="${shortcutName}" /></h3>

    <form action="${getSearchResultURL.toString()}" method="post"  class="tab-le">
        <input type="text" name="ASearchRequest" value='<%= (searchAttr == "") ? "" : searchAttr%>' class="in-put" >
        <spring:message code="btn-search" var="var_search"/>
        <input class="btnl" type="submit" value="${var_search}">
    </form>
    <c:choose>
        <c:when test="${isNull == false}">
            <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" iteratorURL="<%= iteratorURL%>" delta="5" deltaConfigurable="true" total="<%= course.size()%>">
                <liferay-ui:search-container-results>
                    <%
                        results = ListUtil.subList(course, searchContainer.getStart(), searchContainer.getEnd());
                        total = course.size();

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
                        <liferay-ui:search-container-column-text name="lms-label-actions"> 
                            <portlet:renderURL var="editUrl">
                                <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_FORM_COURSE_ITEM_I%>" />  
                                <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                                <portlet:param name="<%= PARAM_ID_ACCOUNT%>" value="${idAccount}" />  
                            </portlet:renderURL>  
                            <portlet:actionURL name="<%= ACTION_REMOVE_COURSE_ITEM_I%>" var="deleteUrl">
                                <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                                <portlet:param name="<%= PARAM_ID_ACCOUNT%>" value="${idAccount}" /> 
                            </portlet:actionURL>
                            <portlet:actionURL name="<%= ACTION_DE_ACTIVATE_ITEM_COURSE%>" var="deactivateUrl"> 
                                <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                                <portlet:param name="<%= PARAM%>" value="1" />
                                <portlet:param name="<%= PARAM_ID_ACCOUNT%>" value="${idAccount}" /> 
                            </portlet:actionURL>
                            <portlet:actionURL name="<%= ACTION_DE_ACTIVATE_ITEM_COURSE%>" var="activateUrl">  
                                <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />  
                                <portlet:param name="<%= PARAM%>" value="0" />
                                <portlet:param name="<%= PARAM_ID_ACCOUNT%>" value="${idAccount}" /> 
                            </portlet:actionURL>  
                            <spring:message code="lms-msg-view-all-user-courses" var="var0" />
                            <spring:message code="mal-deactivate-account-user" var="var1" />
                            <spring:message code="mal-activate-account-user" var="var2" />
                            <spring:message code="map-remove-user-from-course" var="var3" />
                            <liferay-ui:icon-menu>   
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
                                <a class="taglib-icon" href="${deleteQuestionUrl}" onclick="if (confirm('<spring:message code="lms-msg-remove-userI" arguments="${item.nameCourse}"  htmlEscape="true" />')) {
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
    <br/><br/>
    <div class="buttons">
        <button class="btnl"  onclick="window.location = '${backUrl}';"><spring:message code="mal-btn-back-account" /></button> 
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